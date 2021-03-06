package exercise.orders.service.impl;

import exercise.common.exceptions.CustomException;
import exercise.common.services.BaseService;
import exercise.orders.dto.OrderDto;
import exercise.orders.model.OrderEntity;
import exercise.orders.jdbcrepository.JdbcOrderRepository;
import exercise.orders.repository.OrderRepository;
import exercise.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl extends BaseService<OrderRepository,OrderEntity> implements OrderService {

    @Autowired
    private JdbcOrderRepository jdbcOrderRepository;

    public List<OrderDto> getAllOrdersForCustomer(Long customerId){
        Objects.requireNonNull(customerId,"El customer id es requerido");

        List<OrderEntity> lst = repository.findByCustomerId(customerId);

        return convertUtils.convert(lst,OrderDto.class);
    }

    public OrderDto getOrderById(Long orderId){
        Objects.requireNonNull(orderId,"El order id es requerido");
        try {
            return findById(orderId,OrderDto.class);
        } catch (Exception e) {
            throw new CustomException("Error al obtener orden.");
        }
    }

    public OrderDto createOrder(OrderDto dto){
        try {
            return create(dto,OrderDto.class);
        } catch (Exception e) {
            throw new CustomException("Error al crear orden.");
        }
    }

    public OrderDto updateOrder(OrderDto dto){
        try {
            return update(dto.getOrderId(),dto,OrderDto.class);
        } catch (Exception e) {
            throw new CustomException("Error al actualizar orden.");
        }
    }

    public OrderDto getJdbcOrderById(Long orderId){
        Objects.requireNonNull(orderId,"El order id es requerido");

        Optional<OrderDto> opt =jdbcOrderRepository.findByOrderId(orderId);
        if(!opt.isPresent()){
            throw new CustomException("Error al obtener orden.");
        }

        return opt.get();

    }

    public OrderDto getJdbcOrderByOrderId(Long orderId){
        Objects.requireNonNull(orderId,"El order id es requerido");

        return jdbcOrderRepository.findOrderByOrderId(orderId);

    }

    public Long findCustomerIdByOrderId(Long orderId){
        Objects.requireNonNull(orderId,"El order id es requerido");

        return jdbcOrderRepository.findCustomerIdByOrderId(orderId);

    }
}
