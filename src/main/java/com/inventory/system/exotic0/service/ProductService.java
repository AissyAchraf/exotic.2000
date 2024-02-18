package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Category;
import com.inventory.system.exotic0.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    public Product create(Product product);
    public Page<Product> search(String name, int size, int page);
    public Product getById(Long id);
    public Page<Product> searchByCategoryId(Long id, int size, int page);
    public Product update(Product product);

    public void deleteById(Long id);
    public List<Product> findProductsByCategoryAndSubcategories(Category category);
    public Page<Product> findAllByCategoryAndSubcategories(Category category, List<Category> subcategories, Pageable pageable);

}
