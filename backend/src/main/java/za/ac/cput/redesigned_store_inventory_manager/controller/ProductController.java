package za.ac.cput.redesigned_store_inventory_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Product;
import za.ac.cput.redesigned_store_inventory_manager.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public List<Product> getAll() {
        return productService.findAll();
    }

    @PostMapping("/save")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("/read")
    public Optional<Product> read(@RequestParam String productId) {
        return productService.findById(productId);
    }

    @PutMapping("/update")
    public Product update(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String productId) {
        productService.deleteById(productId);
    }
}