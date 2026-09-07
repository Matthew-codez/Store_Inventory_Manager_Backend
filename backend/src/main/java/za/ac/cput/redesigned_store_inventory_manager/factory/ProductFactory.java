/* Product.java
ProductFactory
Author: Zacharia Dipudi (230700659)
Date: 28 June 2026*/
package za.ac.cput.redesigned_store_inventory_manager.factory;

import za.ac.cput.redesigned_store_inventory_manager.domain.Product;


public class ProductFactory {
    public static Product createProduct(String productId, String productName, String productDescription, String productPrice) {
        return new Product.Builder()
                .setProductId(productId)
                .setProductName(productName)
                .setProductDescription(productDescription)
                .setProductPrice(productPrice)
                .build();
    }
}
