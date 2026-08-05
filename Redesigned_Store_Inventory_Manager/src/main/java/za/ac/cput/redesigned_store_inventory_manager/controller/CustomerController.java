package za.ac.cput.redesigned_store_inventory_manager.controller;

/* Customer.java
Customer POJO
Author: Matthew Ferreira (230048870)
Date: 19 July 2026*/

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Customer;
import za.ac.cput.redesigned_store_inventory_manager.service.ICustomerService;

import java.util.List;
import java.util.Optional;

@RestController("api/customers")
public class CustomerController {
    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        Optional<Customer> customers = customerService.findById(id);
        return customers.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer saved = customerService.save(customer);
        if (saved == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id){
        if (!customerService.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
