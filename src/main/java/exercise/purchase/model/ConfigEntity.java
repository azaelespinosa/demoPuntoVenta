package exercise.purchase.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "REST_CONFIG")
public class ConfigEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REST_CONFIG_ID",unique = true, nullable = false)
    private Long configId;

    @Column(name = "API_KEY")
    private String apiKey;

    @Column(name = "active")
    private Boolean active;


}
