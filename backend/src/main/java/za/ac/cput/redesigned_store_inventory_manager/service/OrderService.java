package za.ac.cput.redesigned_store_inventory_manager.service;

/* OrderService.java
Order service implementation
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
    public Optional<Order> findById(String orderNum) {
        if (orderNum == null) return Optional.empty();
        return orderRepository.findById(orderNum);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(String orderNum) {
        if (orderNum == null) return;
        orderRepository.deleteById(orderNum);
    }

    @Override
    public boolean existsById(String orderNum) {
        if (orderNum == null) return false;
        return orderRepository.existsById(orderNum);
    }
}