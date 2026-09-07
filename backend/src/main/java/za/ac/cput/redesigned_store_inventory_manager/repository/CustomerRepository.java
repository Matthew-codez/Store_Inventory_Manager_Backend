package za.ac.cput.redesigned_store_inventory_manager.repository;

/* Customer.java
Customer POJO
Author: Matthew Ferreira (230048870)
Date: 12 July 2026*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.redesigned_store_inventory_manager.domain.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByUsername(String username);
}