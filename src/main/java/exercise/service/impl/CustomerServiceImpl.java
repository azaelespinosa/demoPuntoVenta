package exercise.service.impl;

import exercise.common.exceptions.CustomErrorMessage;
import exercise.common.exceptions.CustomException;
import exercise.common.services.BaseService;
import exercise.dto.CustomerDto;
import exercise.model.CustomerEntity;
import exercise.repository.CustomerRepository;
import exercise.repository.ProductRepository;
import exercise.service.CustomerService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl extends BaseService<CustomerRepository,CustomerEntity> implements CustomerService {

    public CustomerDto getCustomerDetail(long customerId){
        try {
             return findById(customerId, CustomerDto.class);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    public List<CustomerDto> findAllCustomers(){
        try {
            return findAll(CustomerDto.class);
        } catch (Exception e) {
            throw new CustomException("Error al obtener cliente");
        }
    }
}
