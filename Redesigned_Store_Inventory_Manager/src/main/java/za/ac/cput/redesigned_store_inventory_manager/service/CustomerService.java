package za.ac.cput.redesigned_store_inventory_manager.service;

/* Customer.java
Customer POJO
Author: Matthew Ferreira (230048870)
Date: 12 July 2026*/

import org.springframework.stereotype.Service;
import za.ac.cput.redesigned_store_inventory_manager.domain.Customer;
import za.ac.cput.redesigned_store_inventory_manager.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer == null) return null;
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        if (id == null) return null;
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) return;
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) return false;
        return customerRepository.existsById(id);
    }
}
