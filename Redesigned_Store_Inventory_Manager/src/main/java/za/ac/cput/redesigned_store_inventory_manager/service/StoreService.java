package za.ac.cput.redesigned_store_inventory_manager.service;

import org.springframework.stereotype.Service;
import za.ac.cput.redesigned_store_inventory_manager.domain.Store;
import za.ac.cput.redesigned_store_inventory_manager.repository.StoreRepository;
import za.ac.cput.redesigned_store_inventory_manager.service.IStoreService;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService implements IStoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store save(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Optional<Store> findById(String StoreId) {
        if (StoreId == null) return Optional.empty();
        return storeRepository.findById(StoreId);
    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public void deleteById(String StoreId) {
        if (StoreId == null) return;
        storeRepository.deleteById(StoreId);
    }

    @Override
    public boolean existsById(String StoreId) {
        if (StoreId == null) return false;
        return storeRepository.existsById(StoreId);
    }
}





