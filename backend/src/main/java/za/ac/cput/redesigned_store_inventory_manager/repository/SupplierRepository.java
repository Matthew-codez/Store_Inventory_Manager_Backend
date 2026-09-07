package za.ac.cput.redesigned_store_inventory_manager.repository;

/*
 SupplierRepository.java
 Supplier Repository interface
 Author: Templeton Liyabona Dyantyi (222623047)
 Date: 12 July 2026
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.redesigned_store_inventory_manager.domain.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
