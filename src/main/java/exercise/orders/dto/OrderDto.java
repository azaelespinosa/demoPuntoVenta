package exercise.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto extends ResourceSupport  implements Serializable {

    private Long orderId;
    private double price;
    private int quantity;
    private List<OrderDetailDto> orderDetail;

}
