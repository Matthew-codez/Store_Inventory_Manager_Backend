package za.ac.cput.redesigned_store_inventory_manager.controller;

/* OrderController.java
Order REST controller
Author: Matthew Ferreira (230048870)
Date: 19 July 2026*/

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.redesigned_store_inventory_manager.domain.Order;
import za.ac.cput.redesigned_store_inventory_manager.service.IOrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderNum}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderNum){
        Optional<Order> order = orderService.findById(orderNum);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order saved = orderService.save(order);
        if (saved == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{orderNum}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderNum){
        if (!orderService.existsById(orderNum)){
            return ResponseEntity.notFound().build();
        }
        orderService.deleteById(orderNum);
        return ResponseEntity.noContent().build();
    }
}