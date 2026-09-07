package za.ac.cput.redesigned_store_inventory_manager.controller;

/*
 EmployeeController.java
 Employee Controller class
 Author: Templeton Liyabona Dyantyi (222623047)
 Date: 19 July 2026
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Employee;
import za.ac.cput.redesigned_store_inventory_manager.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getAll")
    public List<Employee> getAll() {
        return employeeService.findAll();
    }

    @PostMapping("/save")
    public Employee save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @GetMapping("/read")
    public Employee read(@RequestParam Long id) {
        return employeeService.findById(id).orElse(null);
    }

    @PutMapping("/update")
    public Employee update(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        employeeService.deleteById(id);
    }
}
