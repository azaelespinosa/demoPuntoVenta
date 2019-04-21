package exercise;

import exercise.orders.model.ItemEntity;
import exercise.orders.repository.ItemRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Clase para test's de productos.
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */
@Ignore
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ProductTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ItemRepository itemRepository;


    //TODO Subir a la capa de servicio para probar los test

    @Test
    @Ignore
    public void whenFindById() {

        //given
        ItemEntity product = new ItemEntity();
       // product.setProductId(1L);
        product.setName("Producto 1");
        product.setPrice(2L);
        product.setUpc(112458744L);

        entityManager.persist(product);
        entityManager.flush();

        //when
        Optional<ItemEntity> opti = itemRepository.findById(product.getItemId());

        if(!opti.isPresent()){
            throw new RuntimeException("Error al obtener producto.");
        }

        ItemEntity testEntity =opti.get();

        //then
        assertThat(testEntity.getName()).isEqualTo(testEntity.getName());
    }

    @Test
    @Ignore
    public void whenFindAll() {

        //given
        ItemEntity productUno = new ItemEntity();
        //productUno.setProductId(1L);
        productUno.setName("Producto 1");
        productUno.setPrice(2L);
        productUno.setUpc(4498420248L);
        entityManager.persist(productUno);
        entityManager.flush();

        ItemEntity productDos = new ItemEntity();
        //productDos.setProductId(2L);
        productDos.setName("Producto 2");
        productDos.setPrice(8L);
        productDos.setUpc(665487841L);
        entityManager.persist(productDos);
        entityManager.flush();

        //when
        List<ItemEntity> lstProducts = itemRepository.findAll();

        //then

        assertThat(lstProducts.get(0)).isEqualTo(productUno);
        assertThat(lstProducts.get(1)).isEqualTo(productDos);

    }
}
