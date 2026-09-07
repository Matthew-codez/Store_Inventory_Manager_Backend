package za.ac.cput.redesigned_store_inventory_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Customer;
import za.ac.cput.redesigned_store_inventory_manager.repository.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer-auth")
public class CustomerAuthController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerAuthController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (customerRepository.findByUsername(request.username).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        Customer customer = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName(request.firstName)
                .setSurname(request.surname)
                .setEmail(request.email)
                .setUsername(request.username)
                .setPassword(request.password)
                .build();
        customerRepository.save(customer);
        return ResponseEntity.status(201).body("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<Customer> customer = customerRepository.findByUsername(request.username);
        if (customer.isPresent() && customer.get().getPassword().equals(request.password)) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    public static class RegisterRequest {
        public String firstName, surname, email, username, password;
    }
    public static class LoginRequest {
        public String username, password;
    }
}