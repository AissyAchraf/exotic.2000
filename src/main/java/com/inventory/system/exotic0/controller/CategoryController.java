package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.Category;
import com.inventory.system.exotic0.entity.Image;
import com.inventory.system.exotic0.entity.Product;
import com.inventory.system.exotic0.service.CategoryService;
import com.inventory.system.exotic0.service.ImageService;
import com.inventory.system.exotic0.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductService productService;

    @GetMapping("/categories")
    public String search(Model model,
                         @RequestParam(name="categoryName", defaultValue="") String categoryName,
                         @RequestParam(name="page", defaultValue="0") int page,
                         @RequestParam(name="size", defaultValue="10") int size) {
        Page<Category> categoryPage = categoryService.searchRootCategories("%"+categoryName+"%", size, page);
        List<Category> categoryList = categoryPage.getContent();
        int pages = categoryPage.getTotalPages();
        if(!model.containsAttribute("newCategory")) {
            model.addAttribute("newCategory", new Category());
        }
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("activePage", "Categories");
        model.addAttribute("pages", pages);
        model.addAttribute("size",size);
        model.addAttribute("pageCurrent", page);
        model.addAttribute("categoryName", categoryName);
        return "Categories/search";
    }

    @GetMapping("/search-category-products")
    public String searchCategoryProducts(
            Model model,
            @RequestParam(name="categoryId") Long categoryId,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice
    ) {
        Category category = categoryService.getById(categoryId);
        if(category != null) {
            List<Product> productList = productService.findProductsByCategoryAndSubcategories(category);;

            if(minPrice != null && maxPrice != null) {
                productList = getProductsFilteredByPriceRange(productList, minPrice, maxPrice);
                model.addAttribute("minPrice", minPrice);
                model.addAttribute("maxPrice", maxPrice);
            }
            model.addAttribute("category", category);
            model.addAttribute("productList", productList);
            return "/Products/search";
        }
        return null;
    }

    @GetMapping("/category")
    public String listing(Model model,
                          @RequestParam(name="categoryId") Long categoryId,
                          @RequestParam(name="categoryName", defaultValue="") String categoryName,
                          @RequestParam(name="productsPage", defaultValue="0") int productsPage,
                          @RequestParam(name="subCategoriesPage", defaultValue="0") int subCategoriesPage,
                          @RequestParam(name="size", defaultValue="10") int size) {
        Category category = categoryService.getById(categoryId);
        Page<Category> subCategoriePage = categoryService.searchByParentId(categoryId, size, subCategoriesPage);
        List<Category> subCategoryList = subCategoriePage.getContent();
        int subCategoriesPages = subCategoriePage.getTotalPages();

        Page<Product> productPage = productService.searchByCategoryId(categoryId, size, productsPage);
        List<Product> productList = productPage.getContent();
        int productsPages = subCategoriePage.getTotalPages();
        model.addAttribute("subCategoryList", subCategoryList);
        model.addAttribute("productList", productList);
        model.addAttribute("category", category);
        model.addAttribute("newCategory", new Category());
        model.addAttribute("newProduct", new Product());
        model.addAttribute("activePage", "Categories");
        model.addAttribute("subCategoriesPages", subCategoriesPages);
        model.addAttribute("productsPages", productsPages);
        model.addAttribute("size",size);
        model.addAttribute("productsCurrentPage", productsPage);
        model.addAttribute("subCategoriesCurrentPage", subCategoriesPage);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("activePage", "Categories");
        return "/Categories/view";
    }

    @GetMapping("/create-category")
    public String formCreateCategory(Model model){
        model.addAttribute("category", new Category());
        return "/Categories/create";
    }

    @PostMapping("/create-category")
    public String processCreateCategory(Category category, RedirectAttributes attributes, @RequestParam(name = "categoryImage", required = false) MultipartFile categoryImage) throws SQLException, IOException {

        if(categoryImage != null && !categoryImage.isEmpty()) {
            byte[] bytes = categoryImage.getBytes();
//            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            Image image = new Image();
            image.setImage(bytes);
            Image savedImage = imageService.create(image);
            category.setImage(savedImage);
        }

        categoryService.create(category);
        attributes.addFlashAttribute("successMessage", "Votre categorie : "+category.getName()+" est ajoutée avec succès");
        return "redirect:/categories";
    }

    @PostMapping("/create-subcategory")
    public String processCreateCategory(Category category, RedirectAttributes attributes, @RequestParam(name = "categoryImage", required = false) MultipartFile categoryImage, @RequestParam(name = "parentId") Long parentId) throws SQLException, IOException {

        Category parent = categoryService.getById(parentId);
        category.setParent(parent);

        if(categoryImage != null && !categoryImage.isEmpty()) {
            byte[] bytes = categoryImage.getBytes();
//            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            Image image = new Image();
            image.setImage(bytes);
            Image savedImage = imageService.create(image);
            category.setImage(savedImage);
        }

        categoryService.create(category);
        attributes.addFlashAttribute("successMessage", "Votre sous categorie : "+category.getName()+" est ajoutée avec succès");
        return "redirect:/category?categoryId="+parent.getId();
    }

    @PostMapping("/update-category")
    public String processUpdateCategory(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("name") String name,
            @RequestParam("categoryImage") MultipartFile categoryImage,
            RedirectAttributes attributes
    ) throws IOException, SQLException {
        Category category = categoryService.getById(categoryId);
        Image oldImage = category.getImage();
        Boolean deleteOldImage = false;
        if(category != null)  {
            category.setName(name);
            if(categoryImage != null) {
                byte[] bytes = categoryImage.getBytes();
//                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                Image image = new Image();
                image.setImage(bytes);
                Image savedImage = imageService.create(image);
                category.setImage(savedImage);
                deleteOldImage = true;
            }

            categoryService.update(category);
            if(deleteOldImage && oldImage != null) {
                imageService.delete(oldImage);
            }
            attributes.addFlashAttribute("successMessage", "Votre categorie : "+category.getName()+" est modifiée avec succès");
            if(category.getParent() == null) {
                return "redirect:/categories";
            } else {
                return "redirect:/category?categoryId=" + category.getId();
            }
        }
        attributes.addFlashAttribute("successMessage", "La catégorie est introuvable!");
        return "redirect:/error";
    }

    @GetMapping("/delete-category")
    public String processDeleteCategory(
            @RequestParam("categoryId") Long categoryId,
            RedirectAttributes attributes
    ) {
        Category category = categoryService.getById(categoryId);
        if(category != null) {
            Image image = category.getImage();
            categoryService.delete(categoryId);
            if(image != null) {
                imageService.delete(image);
            }
            attributes.addFlashAttribute("successMessage", "Votre categorie : "+category.getName()+" est supprimée avec succès");
            if(category.getParent() == null) {
                return "redirect:/categories";
            } else {
                return "redirect:/category?categoryId=" + category.getId();
            }
        }
        attributes.addFlashAttribute("successMessage", "La catégorie est introuvable!");
        return "redirect:/error";
    }

    public List<Product> getProductsFilteredByPriceRange(List<Product> productList, int minPrice, int maxPrice) {
        List<Product> removeProducts = new ArrayList<>();
        for (Product product : productList) {
            if(product.getMinSellingPrice() == null || product.getMinSellingPrice() < minPrice || product.getMinSellingPrice() >= maxPrice) {
                removeProducts.add(product);
            }
        }
        productList.removeAll(removeProducts);
        return productList;
    }
}
