package za.ac.cput.redesigned_store_inventory_manager.repository;

/*
 EmployeeRepository.java
 Employee Repository interface
 Author: Templeton Liyabona Dyantyi (222623047)
 Date: 12 July 2026
*/

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.redesigned_store_inventory_manager.domain.Employee;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
}
