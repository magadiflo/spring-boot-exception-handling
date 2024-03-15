package dev.magadiflo.app.service;

import dev.magadiflo.app.persistence.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAllCustomers();

    Customer findCustomerById(Long id);

    Customer findCustomerByEmail(String email);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomerById(Long id);
}
