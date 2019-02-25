package exercise.model;

import exercise.common.entities.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "`ORDER`")
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", nullable = false, unique = true)
    private Long orderId;

    @Column(name = "PRICE", nullable = false)
    private double price;

    @Column(name = "QTY", nullable = false)
    private int quantity;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;
}
