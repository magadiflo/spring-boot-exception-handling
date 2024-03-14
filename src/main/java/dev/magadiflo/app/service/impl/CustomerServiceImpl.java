package dev.magadiflo.app.service.impl;

import dev.magadiflo.app.persistence.entity.Customer;
import dev.magadiflo.app.persistence.repository.CustomerRepository;
import dev.magadiflo.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findCustomerById(Long id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> null);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findCustomerByEmail(String email) {
        return this.customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> null);
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        return this.customerRepository.findById(id)
                .map(customerDB -> {
                    customerDB.setName(customer.getName());
                    customerDB.setEmail(customer.getEmail());
                    if (!customer.getPhoneNumber().isBlank()) {
                        customerDB.setPhoneNumber(customer.getPhoneNumber());
                    }
                    return customerDB;
                })
                .map(this.customerRepository::save)
                .orElseThrow(() -> null);
    }

    @Override
    @Transactional
    public void deleteCustomerById(Long id) {
        this.customerRepository.findById(id)
                .map(customerDB -> {
                    this.customerRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> null);
    }
}