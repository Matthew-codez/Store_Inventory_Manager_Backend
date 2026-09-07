/*
StoreFactoryTest
Author: Zacharia Dipudi (230700659)
Date: 28 June 2026*/
package za.ac.cput.redesigned_store_inventory_manager.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.redesigned_store_inventory_manager.domain.Product;
import za.ac.cput.redesigned_store_inventory_manager.domain.Store;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StoreFactoryTest {


        @Test
        void createStore() {
            Product product = ProductFactory.createProduct("1234","Iphone 17", "Pro max", "R29000");
            Store store = StoreFactory.createStore("410","Istore","0114101754","cape town",product);
            assertNotNull(store);
            assertNotNull(store.getProduct());
            System.out.println(store);
        }
    }

