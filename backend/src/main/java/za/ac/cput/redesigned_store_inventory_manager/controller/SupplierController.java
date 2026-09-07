package za.ac.cput.redesigned_store_inventory_manager.controller;

/*
 SupplierController.java
 Supplier Controller class
 Author: Templeton Liyabona Dyantyi (222623047)
 Date: 19 July 2026
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Supplier;
import za.ac.cput.redesigned_store_inventory_manager.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/getAll")
    public List<Supplier> getAll() {
        return supplierService.findAll();
    }

    @PostMapping("/save")
    public Supplier save(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @GetMapping("/read")
    public Supplier read(@RequestParam String id) {
        return supplierService.findById(id).orElse(null);
    }

    @PutMapping("/update")
    public Supplier update(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String id) {
        supplierService.deleteById(id);
    }
}
