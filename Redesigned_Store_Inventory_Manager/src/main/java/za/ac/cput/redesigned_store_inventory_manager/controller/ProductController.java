package za.ac.cput.redesigned_store_inventory_manager.controller;

import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Product;
import za.ac.cput.redesigned_store_inventory_manager.service.ProductService;

import java.util.List;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    public List<Product>getProduct(){
        return productService.findAll();
    }
    @PostMapping("/save")
    public void create(@RequestBody Product product){
        productService.save(product);
    }
    @GetMapping("/read")
    public void read(@RequestParam String productId){
        productService.findById(productId).orElse(null);
    }
    @PutMapping("/update")
    public void update(@RequestBody Product product){
        productService.save(product);
    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam String productId){
        productService.deleteById(productId);
    }
}
