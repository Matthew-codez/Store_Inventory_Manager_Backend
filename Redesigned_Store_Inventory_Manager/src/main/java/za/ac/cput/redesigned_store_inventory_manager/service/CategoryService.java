package za.ac.cput.redesigned_store_inventory_manager.service;

import java.util.List;
import java.util.Optional;
import za.ac.cput.redesigned_store_inventory_manager.domain.Category;
import za.ac.cput.redesigned_store_inventory_manager.repository.CategoryRepository;
import org.springframework.stereotype.Service;

/* CategoryService.java
Category Service class
Author: Jayden Avontuur (222032278)
Date: 18 July 2026*/

@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        if (category == null) return null;
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        if (id == null) return null;
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) return;
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) return false;
        return categoryRepository.existsById(id);
    }


}
