
/*
StoreFactory
Author: Zacharia Dipudi (230700659)
Date: 28 June 2026*/
package za.ac.cput.redesigned_store_inventory_manager.factory;

import za.ac.cput.redesigned_store_inventory_manager.domain.Product;
import za.ac.cput.redesigned_store_inventory_manager.domain.Store;

public class StoreFactory {
    public static Store createStore(String storeId, String storeName, String storePhoneNumber, String storeLocation, Product product) {

        return new Store.Builder()
                .setStoreId(storeId)
                .setStoreName(storeName)
                .setStorePhoneNumber(storePhoneNumber)
                .setStoreLocation(storeLocation)
                .setProduct(product)
                .Build();
    }
}
