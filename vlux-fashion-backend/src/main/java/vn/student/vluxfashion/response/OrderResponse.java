package vn.student.vluxfashion.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.student.vluxfashion.dto.OrderDto;
import vn.student.vluxfashion.model.Customer;
import vn.student.vluxfashion.model.Guest;
import vn.student.vluxfashion.model.OrderStatus;
import vn.student.vluxfashion.model.PaymentMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String orderId; // Unique identifier for the order
    private String orderNote; // Optional note from the customer
    private PaymentMethod paymentMethod; // Payment method used
    private long totalPrice; // Total amount for the order
    private boolean isPaid; // Payment status
    private OrderStatus orderStatus; // Current status of the order
    private OrderDto orderDetails; // Nested object containing basic order details
    private String guestName; // For guest users


}
