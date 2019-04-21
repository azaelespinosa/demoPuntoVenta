package exercise.orders.model;

import exercise.common.entities.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "`ORDER_DETAIL`")
public class OrderDetailEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DETAIL_ID", nullable = false, unique = true)
    private long orderDetailId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "QTY", nullable = false)
    private Integer qty;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "PRICE")
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "ITEM_ID")
    private ItemEntity item;



}
