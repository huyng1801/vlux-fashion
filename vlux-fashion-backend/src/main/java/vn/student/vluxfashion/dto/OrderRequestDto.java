package vn.student.vluxfashion.dto;
import java.util.List;
public class OrderRequestDto {
    private GuestDto guestDto;
    private OrderDto orderDto;
    private List<OrderItemDto> orderItemDtos;

    // Getters and setters
    public GuestDto getGuestDto() {
        return guestDto;
    }

    public void setGuestDto(GuestDto guestDto) {
        this.guestDto = guestDto;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public void setOrderItemDtos(List<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }
}
