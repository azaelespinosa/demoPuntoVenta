package exercise.orders.service;

import exercise.orders.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto getCustomerDetail(long customerId);

    List<CustomerDto> findAllCustomers();

    CustomerDto createPurchase(CustomerDto dto);
}
