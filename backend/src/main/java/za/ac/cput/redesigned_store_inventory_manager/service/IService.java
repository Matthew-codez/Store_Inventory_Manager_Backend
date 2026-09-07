package za.ac.cput.redesigned_store_inventory_manager.service;
/* Customer.java
Customer POJO
Author: Matthew Ferreira (230048870)
Date: 12 July 2026*/
import java.util.List;
import java.util.Optional;

public interface IService<T, ID> {
    T save(T t);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    boolean existsById(ID id);
}