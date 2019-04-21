package exercise.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Data Tranfer Object para lista de productos.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLst implements Serializable {

    private List<ItemDto> products;

}
