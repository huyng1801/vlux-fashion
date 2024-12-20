package vn.student.vluxfashion.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private GuestDto guestDto;
    private OrderDto orderDto;
    private List<OrderItemDto> orderItemDtos;
}
