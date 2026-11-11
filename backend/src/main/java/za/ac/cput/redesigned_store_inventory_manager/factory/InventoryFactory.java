package za.ac.cput.redesigned_store_inventory_manager.factory;

import za.ac.cput.redesigned_store_inventory_manager.domain.Inventory;
import za.ac.cput.redesigned_store_inventory_manager.domain.Product;
import za.ac.cput.redesigned_store_inventory_manager.domain.Supplier;

import java.time.LocalDate;

public class InventoryFactory {

    public static Inventory createInventory(
            Product product,
            Supplier supplier,
            int quantityInStock,
            double unitPrice,
            String location) {

        if (quantityInStock < 0 || unitPrice < 0)
            return null;

        return new Inventory.Builder(1L) // placeholder ID (DB will generate real one)
                .product(product)
                .supplier(supplier)
                .quantityInStock(quantityInStock)
                .unitPrice(unitPrice)
                .location(location)
                .build();
    }
}