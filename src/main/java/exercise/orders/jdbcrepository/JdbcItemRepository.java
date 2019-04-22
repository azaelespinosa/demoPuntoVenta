package exercise.orders.jdbcrepository;

import exercise.orders.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Obtencion de datos con JDBC Template sin declarar un Bean
 */

@Repository
public class JdbcItemRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public String findProductNameById(Long productId){

        String sql = "SELECT NAME FROM `ITEM` WHERE ITEM_ID = ?";

        String name = jdbcTemplate.queryForObject(
                sql, new Object[] { productId }, String.class);

        return name;

    }

    public ItemDto findProductById(Long productId){

        String sql = "SELECT * FROM `ITEM` WHERE ITEM_ID = ?";

        ItemDto itemDto = (ItemDto)jdbcTemplate.queryForObject(
                sql, new Object[] { productId },
                new BeanPropertyRowMapper(ItemDto.class));

        return itemDto;

    }


}
