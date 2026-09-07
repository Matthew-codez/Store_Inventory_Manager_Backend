package za.ac.cput.redesigned_store_inventory_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Inventory;
import za.ac.cput.redesigned_store_inventory_manager.domain.Order;
import za.ac.cput.redesigned_store_inventory_manager.repository.InventoryRepository;
import za.ac.cput.redesigned_store_inventory_manager.repository.OrderRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PurchaseController(InventoryRepository inventoryRepository, OrderRepository orderRepository) {
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> purchase(@RequestBody PurchaseRequest request) {
        int updated = inventoryRepository.decrementStock(request.inventoryId, request.quantity);
        if (updated == 0) {
            return ResponseEntity.status(400).body("Not enough stock available");
        }

        Optional<Inventory> invOpt = inventoryRepository.findById(request.inventoryId);
        if (invOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Item not found");
        }
        Inventory inv = invOpt.get();
        double total = inv.getUnitPrice() * request.quantity;
        String productId = inv.getProduct() != null ? inv.getProduct().getProductId() : "Unknown";

        Order order = new Order.Builder()
                .setOrderNum(UUID.randomUUID().toString())
                .setCustomerId(request.customerId)
                .setItem(productId)
                .setQuantity(request.quantity)
                .setOrderDate(LocalDate.now().toString())
                .setDeliveryDate("")
                .setStatus("Completed")
                .setTotalAmount(total)
                .build();

        orderRepository.save(order);
        return ResponseEntity.ok("Purchase successful");
    }

    public static class PurchaseRequest {
        public String customerId;
        public Long inventoryId;
        public int quantity;
    }
}
