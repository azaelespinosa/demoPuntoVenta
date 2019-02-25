package exercise.service;

import exercise.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto getCustomerDetail(long customerId);

    List<CustomerDto> findAllCustomers();
}
