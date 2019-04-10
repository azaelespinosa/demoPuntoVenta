package exercise.repository.impl;

import exercise.dto.OrderDto;
import exercise.repository.JdbcOrderRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Transactional
public class JdbcOrderRepositoryImpl implements JdbcOrderRepository {

    JdbcTemplate jdbcTemplate;

    final String QUERY_FIND_BY_ORDER_ID = "SELECT ORDER_ID, PRICE, QTY, CUSTOMER_ID, PRODUCT_ID FROM `ORDER` WHERE ORDER_ID = ?";

    @Override
    public Optional<OrderDto> findByOrderId(Long orderId) {
        try {
            return jdbcTemplate.query(
                    QUERY_FIND_BY_ORDER_ID,
                    Arrays.asList(
                            orderId
                    ).toArray(),
                    (rs, i) -> OrderDto.builder()
                            .orderId(rs.getLong("ORDER_ID"))
                            .price(rs.getLong("PRICE"))
                            .quantity(rs.getInt("QTY"))
                            .build()
            ).stream().findAny();
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }


    public Long findCustomerIdByOrderId(Long orderId){

        String sql = "SELECT CUSTOMER_ID FROM `ORDER` WHERE ORDER_ID = ?";

        Long customerId = jdbcTemplate.queryForObject(
                sql, new Object[] { orderId }, Long.class);

        return customerId;

    }

    public OrderDto findOrderByOrderId(Long orderId){

        String sql = "SELECT * FROM `ORDER` WHERE ORDER_ID = ?";

        OrderDto orderDto = (OrderDto)jdbcTemplate.queryForObject(
                sql, new Object[] { orderId },
                new BeanPropertyRowMapper(OrderDto.class));

        return orderDto;
    }


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

