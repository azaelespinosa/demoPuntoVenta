package exercise.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseDto extends ResourceSupport  implements Serializable {

    private String purchaseId;
    private String userId;
    private Long orderId;
    private String transNum;
    private BigDecimal subTotal;
    private BigDecimal iva;
    private BigDecimal total;
    private int status;
    private Date transDate;
    private Long clienteId;
    private String paymentForm;
    private String paymentType;
    private List<PurchaseDetailDto> detailItems;

}
