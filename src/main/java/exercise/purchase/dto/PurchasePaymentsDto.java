package exercise.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchasePaymentsDto  implements Serializable {

    private String purchasePaymentId;
    private String purchaseId;
    private String description;
    private BigDecimal total;
    private BigDecimal totalIva;
    private String paymentForm;
    private String paymentType;

}
