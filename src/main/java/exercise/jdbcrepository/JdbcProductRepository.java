package exercise.jdbcrepository;

import exercise.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public String findProductNameById(Long productId){

        String sql = "SELECT NAME FROM `PRODUCT` WHERE PRODUCT_ID = ?";

        String name = jdbcTemplate.queryForObject(
                sql, new Object[] { productId }, String.class);

        return name;

    }

    public ProductDto findProductById(Long productId){

        String sql = "SELECT * FROM `PRODUCT` WHERE PRODUCT_ID = ?";

        ProductDto productDto = (ProductDto)jdbcTemplate.queryForObject(
                sql, new Object[] { productId },
                new BeanPropertyRowMapper(ProductDto.class));

        return productDto;

    }


}
