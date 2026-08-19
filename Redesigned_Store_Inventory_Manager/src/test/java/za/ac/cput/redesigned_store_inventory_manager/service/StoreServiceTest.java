package za.ac.cput.redesigned_store_inventory_manager.service;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.redesigned_store_inventory_manager.domain.Product;
import za.ac.cput.redesigned_store_inventory_manager.domain.Store;
import za.ac.cput.redesigned_store_inventory_manager.factory.ProductFactory;
import za.ac.cput.redesigned_store_inventory_manager.factory.StoreFactory;

import java.util.Optional;

import static java.lang.String.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StoreServiceTest {
    @Autowired
    private StoreService service;

    public static Product product = ProductFactory.createProduct("1322","Iphone","model17","21,000");


    public static Store store= StoreFactory.createStore("1234","Istore", "0114101754","Cape town",product);

    @Test
    @Order(1)
    void save() {

        Store created = service.save(store);
        assertNotNull(created);
        System.out.println("Saved Store: " + created);
    }

    @Test
    @Order(2)
    void read() {

        Optional<Store> read = service.findById(store.getStoreId());
        assertTrue(read.isPresent());
        System.out.println("Read Store: " + read.get());
    }

    @Test
    @Order(3)
    void update() {

        // Change the store information
        Store updatedStore = StoreFactory.createStore(
                "1234",
                "Updated Istore",
                "0114101754",
                "Cape Town",
                product
        );

        // Save the updated store
        Store result = service.save(updatedStore);
        assertNotNull(result);
        System.out.println("Updated Store: " + result);
    }


    @Test
    @Order(4)
    void delete() {

        service.deleteById(store.getStoreId());
        boolean exists = service.existsById(store.getStoreId());
        assertFalse(exists);
        System.out.println("Store deleted successfully");
    }

}
