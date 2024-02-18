package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Category;
import com.inventory.system.exotic0.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public Category create(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Page<Category> search(String name, int size, int page) {
        return categoryRepo.search(name, PageRequest.of(page, size));
    }

    @Override
    public Category getById(Long id) {
        return categoryRepo.getById(id);
    }

    @Override
    public Page<Category> searchByParentId(Long parentId, int size, int page) {
        return categoryRepo.searchByParentId(parentId, PageRequest.of(page, size));
    }

    @Override
    public Page<Category> searchRootCategories(String name, int size, int page) {
        return categoryRepo.searchRootCategories(name, PageRequest.of(page, size));
    }

    @Override
    public Category update(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepo.delete(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public List<Category> findRootCategories() {
        return categoryRepo.findRootCategories();
    }

}
