package za.ac.cput.redesigned_store_inventory_manager.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.redesigned_store_inventory_manager.domain.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InventoryFactoryTest {

    @Test
    public void createInventory() {

        Product product = ProductFactory.createProduct("P1", "Mouse", "Electronics");
        Supplier supplier = new Supplier();

        Inventory inventory = InventoryFactory.createInventory(
                product,
                supplier,
                50,
                10,
                "Warehouse A"
        );

        assertNotNull(inventory);
        assertEquals(50, inventory.getQuantityInStock());
        assertEquals("Electronics", inventory.getProduct().getCategory());
    }

    @Test
    public void createInventory_InvalidQuantity() {

        Product product = new Product();
        Supplier supplier = new Supplier();

        Inventory inventory = InventoryFactory.createInventory(
                product,
                supplier,
                -5, // invalid
                10,
                "Warehouse A"
        );

        assertNull(inventory);
    }
}