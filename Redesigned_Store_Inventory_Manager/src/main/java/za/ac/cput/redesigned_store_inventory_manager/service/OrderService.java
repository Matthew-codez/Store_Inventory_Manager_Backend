package za.ac.cput.redesigned_store_inventory_manager.service;

/* Customer.java
Customer POJO
Author: Matthew Ferreira (230048870)
Date: 12 July 2026*/

import org.springframework.stereotype.Service;
import za.ac.cput.redesigned_store_inventory_manager.domain.Order;
import za.ac.cput.redesigned_store_inventory_manager.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        if (id == null) return Optional.empty();
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) return;
        orderRepository.deleteById(id);
    }
    @Override
    public boolean existsById(Long id) {
        if (id == null) return false;
        return orderRepository.existsById(id);
    }
}
