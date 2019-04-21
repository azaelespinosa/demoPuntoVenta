package exercise.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * Data Tranfer Object para Producto.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemDto implements Serializable {

    private Long itemId;
    private Long price;
    private String name;
    private Long upc;

    private DiscountDto discount;

}
