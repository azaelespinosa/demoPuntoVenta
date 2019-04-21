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
public class PurchaseDetailDto implements Serializable {

    private String purchaseItemId;
    private String purchaseId;
    private Long itemId;
    private int qty;
    private BigDecimal price;
}
