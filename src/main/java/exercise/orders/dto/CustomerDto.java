package exercise.orders.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDto extends ResourceSupport implements Serializable {

    private Long customerId;
    private String customerName;
    private String companyName;
}
