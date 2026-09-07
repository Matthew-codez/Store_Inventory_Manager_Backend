package za.ac.cput.redesigned_store_inventory_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Store;
import za.ac.cput.redesigned_store_inventory_manager.service.StoreService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/stores")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/getAll")
    public List<Store> getAll() {
        return storeService.findAll();
    }

    @PostMapping("/save")
    public Store save(@RequestBody Store store) {
        return storeService.save(store);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String storeId) {
        storeService.deleteById(storeId);
    }

    @GetMapping("/read")
    public Optional<Store> read(@RequestParam String storeId) {
        return storeService.findById(storeId);
    }

    @PutMapping("/update")
    public Store update(@RequestBody Store store) {
        return storeService.save(store);
    }
}