package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Category;
import com.inventory.system.exotic0.entity.Product;
import com.inventory.system.exotic0.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> search(String name, int size, int page) {
        return productRepository.search(name, PageRequest.of(page, size));
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public Page<Product> searchByCategoryId(Long id, int size, int page) {
        return productRepository.searchByCategory(id, PageRequest.of(page, size));
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductsByCategoryAndSubcategories(Category category) {
        List<Product> allProducts = new ArrayList<>();
        getAllProductsRecursive(category, allProducts);
        return allProducts;
    }

    @Override
    public Page<Product> findAllByCategoryAndSubcategories(Category category, List<Category> subcategories, Pageable pageable) {
        return productRepository.findAllByCategoryAndSubCategories(category.getId(), pageable);
    }

    private void getAllProductsRecursive(Category category, List<Product> allProducts) {
        List<Product> productsInCategory = productRepository.findProductsByCategory(category);
        allProducts.addAll(productsInCategory);

        List<Category> subcategories = category.getComponents();
        if (subcategories != null) {
            for (Category subcategory : subcategories) {
                getAllProductsRecursive(subcategory, allProducts);
            }
        }
    }

}
