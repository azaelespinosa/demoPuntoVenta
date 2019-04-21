package exercise.orders.model;

import exercise.common.entities.BaseEntity;
import lombok.*;

import javax.persistence.*;

/**
 * Entidad para Descuentos
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "DISCOUNT")
public class DiscountEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISCOUNT_ID",unique = true, nullable = false)
    private Long discountId;

    @Column(name = "VALUE")
    private Long value;




}
