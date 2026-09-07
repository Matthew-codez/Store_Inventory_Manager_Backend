package za.ac.cput.redesigned_store_inventory_manager.factory;

/*
 SupplierFactoryTest.java
 Supplier Factory Test class
 Author: Templeton Liyabona Dyantyi (222623047)
 Date: 28 June 2026
*/

import org.junit.jupiter.api.Test;
import za.ac.cput.redesigned_store_inventory_manager.domain.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class SupplierFactoryTest {

    @Test
    void createSupplier() {

        Supplier supplier = SupplierFactory.createSupplier(
                "SUP001",
                "ABC Suppliers",
                "021-555-1234"
        );

        assertNotNull(supplier);
        assertEquals("SUP001", supplier.getSupplierId());
        assertEquals("ABC Suppliers", supplier.getSupplierName());
    }

    @Test
    void createSupplierWithEmptyValues() {

        Supplier supplier = SupplierFactory.createSupplier(
                "",
                "",
                ""
        );

        assertNull(supplier);
    }
}
