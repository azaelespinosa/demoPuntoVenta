package exercise.orders.model;

import exercise.common.entities.BaseEntity;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Entidad para Productos
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "ITEM")
public class ItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID", nullable = false, unique = true)
    private Long itemId;

    @Column(name = "PRICE", nullable = false)
    private Long price;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "UPC", nullable = false)
    private Long upc;


    @OneToOne(cascade=CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "DISCOUNT_ID")
    private DiscountEntity discount;


}
