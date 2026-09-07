package za.ac.cput.redesigned_store_inventory_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Employee;
import za.ac.cput.redesigned_store_inventory_manager.repository.EmployeeRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public AuthController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (employeeRepository.findByUsername(request.username).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }

        Employee employee = new Employee.Builder()
                .setEmployeeName(request.username)
                .setPosition("Staff")
                .setSalary(0.0)
                .setUsername(request.username)
                .setPassword(request.password)
                .build();

        employeeRepository.save(employee);
        return ResponseEntity.status(201).body("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<Employee> employee = employeeRepository.findByUsername(request.username);

        if (employee.isPresent() && employee.get().getPassword().equals(request.password)) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    public static class RegisterRequest {
        public String username;
        public String email;
        public String password;
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }
}