package exercise.orders.service.impl;

import exercise.aspects.RequestLog;
import exercise.aspects.Time;
import exercise.common.exceptions.CustomException;
import exercise.common.services.BaseService;
import exercise.orders.dto.CustomerDto;
import exercise.orders.model.CustomerEntity;
import exercise.orders.repository.CustomerRepository;
import exercise.orders.service.CustomerService;
import exercise.purchase.dto.PurchaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl extends BaseService<CustomerRepository,CustomerEntity> implements CustomerService {

    public CustomerDto getCustomerDetail(long customerId){
        try {
             return findById(customerId, CustomerDto.class);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    @Time
    @RequestLog
    @Transactional
    public CustomerDto createPurchase(CustomerDto dto){

        log.info("Method createPurchase iniciando.");

        if (Optional.ofNullable(dto).map(CustomerDto::getCustomerName).map(t -> t == null).orElse(true)) {
            throw new RuntimeException("Favor de enviar un nombre valido.");
        }

        try {
            return create(dto,CustomerDto.class);

        }catch(Exception e){
            throw new CustomException("Ocurrio un error al crear el customer.");
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
