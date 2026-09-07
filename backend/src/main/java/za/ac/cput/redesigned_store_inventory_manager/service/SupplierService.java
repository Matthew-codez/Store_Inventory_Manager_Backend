package za.ac.cput.redesigned_store_inventory_manager.service;

import org.springframework.stereotype.Service;
import za.ac.cput.redesigned_store_inventory_manager.domain.Supplier;
import za.ac.cput.redesigned_store_inventory_manager.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

/*
 SupplierService.java
 Supplier Service class
 Author: Templeton Liyabona Dyantyi (222623047)
 Date: 12 July 2026
*/

@Service
public class SupplierService implements ISupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier save(Supplier supplier) {
        if (supplier == null) return null;
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<Supplier> findById(String id) {
        if (id == null) return Optional.empty();
        return supplierRepository.findById(id);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        if (id == null) return;
        supplierRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        if (id == null) return false;
        return supplierRepository.existsById(id);
    }
}
