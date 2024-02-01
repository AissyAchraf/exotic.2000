package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.Category;
import com.inventory.system.exotic0.repository.CategoryRepository;
import com.inventory.system.exotic0.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute("categoriesList")
    public List<Category> addGlobalAttributes(Model model) {
        return categoryRepository.findRootCategories();
    }
}
