package za.ac.cput.redesigned_store_inventory_manager.factory;

import za.ac.cput.redesigned_store_inventory_manager.domain.Supplier;

/*
 SupplierFactory.java
 Supplier Factory class
 Author: Templeton Liyabona Dyantyi (222623047)
 Date: 28 June 2026
*/

public class SupplierFactory {

    public static Supplier createSupplier(
            String supplierId,
            String supplierName,
            String contactDetails) {

        if (supplierId == null || supplierId.isBlank())
            return null;

        if (supplierName == null || supplierName.isBlank())
            return null;

        if (contactDetails == null || contactDetails.isBlank())
            return null;

        return new Supplier.Builder()
                .setSupplierId(supplierId)
                .setSupplierName(supplierName)
                .setContactDetails(contactDetails)
                .build();
    }
}