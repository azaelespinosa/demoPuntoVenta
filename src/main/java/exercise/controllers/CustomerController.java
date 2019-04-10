package exercise.controllers;

import exercise.dto.CustomerDto;
import exercise.dto.OrderDto;
import exercise.service.CustomerService;
import exercise.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/{customerId}")
    @ApiOperation(value = "Busca un cliente por id.")
    public CustomerDto getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerDetail(customerId);
    }

    @GetMapping(value = "/order/{orderId}" )
    @ApiOperation(value = "Busca una orden por id.")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping(value="/order")
    @ApiOperation(value = "Crea una orden.")
    public OrderDto createOrder(@RequestBody OrderDto dto){
        return  orderService.createOrder(dto);
    }

    @PutMapping(value="/order")
    @ApiOperation(value = "Actualiza una orden.")
    public OrderDto updateOrder(@RequestBody OrderDto dto){
        return  orderService.updateOrder(dto);
    }

    @GetMapping(value = "/{customerId}/orders",    produces = {"application/hal+json"})
    @ApiOperation(value = "Busca las ordenes del cliente - HATEOAS.")
    public Resources<OrderDto> getOrdersForCustomer(@PathVariable final Long customerId) {

        List<OrderDto> orders = orderService.getAllOrdersForCustomer(customerId);
        for (final OrderDto order : orders) {

            Link selfLink = linkTo(methodOn(CustomerController.class).getOrderById(order.getOrderId())).withSelfRel();
            order.add(selfLink);

        }

        Link link = linkTo(methodOn(CustomerController.class).getOrdersForCustomer(customerId)).withSelfRel();
        Resources<OrderDto> result = new Resources<OrderDto>(orders, link);

        return result;
    }

    @GetMapping(value = "/all", produces = { "application/hal+json" })
    @ApiOperation(value = "Todos los clientes y sus ordenes. - HATEOAS.")
    public Resources<CustomerDto> getAllCustomers() {
        List<CustomerDto> allCustomers = customerService.findAllCustomers();

        for (CustomerDto customer : allCustomers) {
            Long customerId = customer.getCustomerId();
            Link selfLink = linkTo(CustomerController.class).slash(customerId).withSelfRel();
            customer.add(selfLink);
            if (orderService.getAllOrdersForCustomer(customerId).size() > 0) {
                Link ordersLink = linkTo(methodOn(CustomerController.class)
                        .getOrdersForCustomer(customerId)).withRel("allOrders");
                customer.add(ordersLink);
            }
        }

        Link link = linkTo(CustomerController.class).withSelfRel();
        Resources<CustomerDto> result = new Resources<CustomerDto>(allCustomers, link);
        return result;
    }

    @GetMapping(value = "/order/jdbc/{orderId}" )
    @ApiOperation(value = "Busca una orden por id via jdbc con labmda")
    public OrderDto getJdbcOrderById(@PathVariable Long orderId) {
        return orderService.getJdbcOrderById(orderId);
    }

    @GetMapping(value = "/order/jdbc/id/{orderId}" )
    @ApiOperation(value = "Busca una orden por id via jdbc con queryForObject y BeanPropertyRowMapper")
    public OrderDto getJdbcOrderByOrderId(@PathVariable Long orderId) {
        return orderService.getJdbcOrderByOrderId(orderId);
    }


    @GetMapping(value = "/order/jdbc/customer/{orderId}" )
    @ApiOperation(value = "Busca una customer id por orderid via jdbc con queryForObject")
    public Long findCustomerIdByOrderId(@PathVariable Long orderId) {
        return orderService.findCustomerIdByOrderId(orderId);
    }
}
