package za.ac.cput.redesigned_store_inventory_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Store;
import za.ac.cput.redesigned_store_inventory_manager.service.StoreService;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/employee")

public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService){
            this.storeService=storeService;
    }
    @GetMapping("/getAll")
    public List<Store> getAll(){
        return storeService.findAll();
    }
    @PostMapping("/save")
    public void save(@RequestBody Store store){
        storeService.save(store);
    }
    @DeleteMapping("delete")
    public void delete(@RequestParam String StoreId){
      storeService.deleteById(StoreId);
    }
    @GetMapping("/read")
    public void read(@RequestParam String StoreId){
        storeService.findById(StoreId);
    }
    @PutMapping("/update")
    public void update(Store store){
        storeService.save(store);
    }

}
