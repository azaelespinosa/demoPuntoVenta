package exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Data Tranfer Object para Descuento.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DiscountDto implements Serializable{

    private Long discountId;

    private Long value;
}
