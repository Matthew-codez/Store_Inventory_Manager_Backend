package za.ac.cput.redesigned_store_inventory_manager.service;

import org.springframework.stereotype.Service;
import za.ac.cput.redesigned_store_inventory_manager.domain.Employee;
import za.ac.cput.redesigned_store_inventory_manager.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

/*
 EmployeeService.java
 Employee Service class
 Author: Templeton Liyabona Dyantyi (222623047)
 Date: 12 July 2026
*/

@Service
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        if (employee == null) return null;
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        if (id == null) return Optional.empty();
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) return;
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) return false;
        return employeeRepository.existsById(id);
    }
}
