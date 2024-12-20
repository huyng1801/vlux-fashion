package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.student.vluxfashion.dto.GuestDto;
import vn.student.vluxfashion.dto.OrderDto;
import vn.student.vluxfashion.dto.OrderItemDto;
import vn.student.vluxfashion.model.Guest;
import vn.student.vluxfashion.model.Order;
import vn.student.vluxfashion.model.OrderItem;
import vn.student.vluxfashion.model.OrderStatus;
import vn.student.vluxfashion.model.PaymentMethod;
import vn.student.vluxfashion.model.ProductSize;
import vn.student.vluxfashion.repository.GuestRepository;
import vn.student.vluxfashion.repository.OrderRepository;
import vn.student.vluxfashion.repository.OrderItemRepository;
import vn.student.vluxfashion.repository.ProductSizeRepository;
import vn.student.vluxfashion.response.OrderItemResponse;
import vn.student.vluxfashion.response.OrderResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Transactional
    public OrderResponse createOrder(GuestDto guestDto, OrderDto orderDto, List<OrderItemDto> orderItemDtos) {
        // Create and save the guest entity from GuestDto
        Guest guest = new Guest();
        guest.setFullName(guestDto.getFullName());
        guest.setEmail(guestDto.getEmail());
        guest.setPhone(guestDto.getPhone());
        guest.setAddress(guestDto.getAddress());
        guest.setAddress2(guestDto.getAddress2());
        guest.setCity(guestDto.getCity());
        guest.setCreatedAt(new Date());
        guest.setUpdatedAt(new Date());

        Guest savedGuest = guestRepository.save(guest);

        // Generate Order ID based on current date and time (ddMMyyyyHHmmss)
        String orderId = generateOrderId();

        // Create Order entity and map from OrderDto
        Order order = new Order();
        order.setOrderId(orderId);
        order.setGuest(savedGuest); // Associate the guest with the order
        order.setTotalPrice(calculateTotalPrice(orderItemDtos)); // Calculate total price
        order.setIsPaid(orderDto.isPaid());
        order.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
        order.setOrderNote("");
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());

        // Save the order to the database
        Order savedOrder = orderRepository.save(order);

        // Map and save order items from OrderItemDto
        List<OrderItem> orderItems = orderItemDtos.stream().map(orderItemDto -> {
            ProductSize productSize = productSizeRepository.findById(orderItemDto.getProductSizeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product size ID"));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProductSize(productSize);
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setUnitPrice(orderItemDto.getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        // Save all order items
        orderItemRepository.saveAll(orderItems);

        // Return an OrderResponse instead of just the orderId
        return mapOrderToResponse(savedOrder, savedGuest);
    }

    private String generateOrderId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        return dateFormat.format(new Date());
    }

    private long calculateTotalPrice(List<OrderItemDto> orderItemDtos) {
        return orderItemDtos.stream()
                .mapToLong(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    private OrderResponse mapOrderToResponse(Order savedOrder, Guest savedGuest) {
        // Map savedOrder and guest to OrderResponse
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(savedOrder.getOrderId());
        orderResponse.setOrderNote(savedOrder.getOrderNote());
        orderResponse.setPaymentMethod(savedOrder.getPaymentMethod());
        orderResponse.setTotalPrice(savedOrder.getTotalPrice());

        orderResponse.setOrderStatus(savedOrder.getOrderStatus());

        if (savedGuest != null) {
            orderResponse.setGuestName(savedGuest.getFullName());
        }
        return orderResponse;
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> mapOrderToResponse(order, order.getGuest()))
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .map(order -> mapOrderToResponse(order, order.getGuest()))
                .orElse(null);
    }

    private OrderItemResponse mapOrderItemToResponse(OrderItem orderItem) {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setProductName(orderItem.getProductSize().getProductColor().getProduct().getProductName()); 
                                                                                         
        orderItemResponse.setColorName(orderItem.getProductSize().getProductColor().getColorName()); 
                                                                                      
        orderItemResponse.setSizeValue(orderItem.getProductSize().getSizeValue());
                                                                                     
        orderItemResponse.setQuantity(orderItem.getQuantity());
        orderItemResponse.setUnitPrice(orderItem.getUnitPrice());
        return orderItemResponse;
    }

    public List<OrderItemResponse> getOrderItemsByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        return orderItemRepository.findByOrder(order).stream()
                .map(this::mapOrderItemToResponse)
                .collect(Collectors.toList());
    }

}
