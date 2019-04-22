package exercise.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetailDto {

    private long orderDetailId;
    private Long orderId;
    private Integer qty;
    private Long itemId;
    private String note;
    private BigDecimal price;
    private ItemDto item;


}
