package za.ac.cput.redesigned_store_inventory_manager.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import za.ac.cput.redesigned_store_inventory_manager.domain.Inventory;
import za.ac.cput.redesigned_store_inventory_manager.repository.InventoryRepository;

/* InventoryService.java
Inventory Service class
Author: Jayden Avontuur (222032278)
Date: 18 July 2026*/

@Service
public class InventoryService implements IInventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory save(Inventory inventory) {
        if (inventory == null) return null;
        return inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        if (id == null) return null;
        return inventoryRepository.findById(id);
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) return;
        inventoryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) return false;
        return inventoryRepository.existsById(id);
    }


}
