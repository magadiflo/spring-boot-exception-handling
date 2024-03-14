package dev.magadiflo.app.web.api;

import dev.magadiflo.app.persistence.entity.Customer;
import dev.magadiflo.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers() {
        return ResponseEntity.ok(this.customerService.findAllCustomers());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(this.customerService.findCustomerById(id));
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(this.customerService.findCustomerByEmail(email));
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        Customer customerDB = this.customerService.saveCustomer(customer);
        URI location = URI.create("/api/v1/customers/" + customerDB.getId());
        return ResponseEntity.created(location).body(customerDB);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return ResponseEntity.ok(this.customerService.updateCustomer(id, customer));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        this.customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}
