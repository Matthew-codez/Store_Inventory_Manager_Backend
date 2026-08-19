package za.ac.cput.redesigned_store_inventory_manager.service;
/**
 *
 * @author Zacharia Dipudi
 */
import org.springframework.stereotype.Service;
import za.ac.cput.redesigned_store_inventory_manager.domain.Product;
import za.ac.cput.redesigned_store_inventory_manager.repository.ProductRepository;
import za.ac.cput.redesigned_store_inventory_manager.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ProductService implements IProductService{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;


    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(String storeId) {
        if (id == null) return null;
        return productRepository.findById(storeId);
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(String StoreId) {
        if (id == null) return;
        productRepository.deleteById(StoreId);
    }

    @Override
    public boolean existsById(String StoreId) {
        if (id == null) return false;
        return productRepository.existsById(StoreId);
    }
}



