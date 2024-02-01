package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    public Category create(Category category);
    public Page<Category> search(String name, int size, int page);
    public Category getById(Long id);
    public Page<Category> searchByParentId(Long parentId, int size, int page);

    Page<Category> searchRootCategories(String name, int size, int page);
    public Category update(Category category);
    public void delete(Long id);
    public List<Category> findAll();
}
