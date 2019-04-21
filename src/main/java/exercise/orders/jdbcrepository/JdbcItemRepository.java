package exercise.orders.jdbcrepository;

import exercise.orders.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcItemRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public String findProductNameById(Long productId){

        String sql = "SELECT NAME FROM `PRODUCT` WHERE PRODUCT_ID = ?";

        String name = jdbcTemplate.queryForObject(
                sql, new Object[] { productId }, String.class);

        return name;

    }

    public ItemDto findProductById(Long productId){

        String sql = "SELECT * FROM `PRODUCT` WHERE PRODUCT_ID = ?";

        ItemDto itemDto = (ItemDto)jdbcTemplate.queryForObject(
                sql, new Object[] { productId },
                new BeanPropertyRowMapper(ItemDto.class));

        return itemDto;

    }


}
