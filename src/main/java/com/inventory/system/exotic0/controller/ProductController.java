package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.Category;
import com.inventory.system.exotic0.entity.Image;
import com.inventory.system.exotic0.entity.Product;
import com.inventory.system.exotic0.entity.ProductVariant;
import com.inventory.system.exotic0.service.CategoryService;
import com.inventory.system.exotic0.service.ImageService;
import com.inventory.system.exotic0.service.ProductService;
import com.inventory.system.exotic0.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductVariantService productVariantService;

    @PostMapping("/create-product")
    public String processCreateProduct(Product product,
                                       RedirectAttributes attributes,
                                       @RequestParam(name = "productImage", required = false) MultipartFile productImage,
                                       @RequestParam(name = "parentId") Long parentId) throws IOException, SQLException {
        Category parent = categoryService.getById(parentId);
        product.setCategory(parent);
        product.setVariants(new ArrayList<>());

        if(productImage != null && !productImage.isEmpty()) {
            byte[] bytes = productImage.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            Image image = new Image();
            image.setImage(blob);
            Image savedImage = imageService.create(image);
            product.setImage(savedImage);
        }

        productService.create(product);
        attributes.addFlashAttribute("successMessage", "Votre produit : "+product.getName()+" est ajoutée avec succès");
        return "redirect:/category?categoryId="+parent.getId();
    }

    @GetMapping("/product")
    public String view(@RequestParam("productId") Long productId, Model model) {
        Product product = productService.getById(productId);
        model.addAttribute("product", product);
        model.addAttribute("newProductVariant", new ProductVariant());
        return "/Products/view";
    }

    @GetMapping("/delete-product")
    public String deleteProduct(@RequestParam("productId") Long productId, RedirectAttributes attributes) {
        Product product = productService.getById(productId);
        if(product != null) {
            productService.deleteById(productId);
            attributes.addFlashAttribute("successMessage", "Votre produit : "+product.getName()+" est supprimé avec succès");
            return "redirect:/category?categoryId="+product.getCategory().getId();
        }
        attributes.addFlashAttribute("errorMessage", "Le produit n'est pas trouvé!");
        return "redirect:/category?categoryId="+product.getCategory().getId();
    }

    @PostMapping("/create-variant")
    public String processCreateVariant(
            ProductVariant productVariant,
            RedirectAttributes attributes,
            @RequestParam("productId") Long productId,
            @RequestParam("variantImage") MultipartFile variantImage
    ) throws IOException, SQLException {
        Product parent = productService.getById(productId);
        productVariant.setProduct(parent);
        productVariant.setStocks(new ArrayList<>());

        if(variantImage != null && !variantImage.isEmpty()) {
            byte[] bytes = variantImage.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            Image image = new Image();
            image.setImage(blob);
            Image savedImage = imageService.create(image);
            productVariant.setImage(savedImage);
        }

        productVariantService.create(productVariant);
        attributes.addFlashAttribute("successMessage", "Votre variante avec le code barre : "+productVariant.getBarcode()+" est ajoutée avec succès");
        return "redirect:/product?productId="+parent.getId();
    }

    @PostMapping("/update-variant")
    public String processUpdateVariant(@RequestParam("productVariantId") Long productVariantId,
                                       @RequestParam("size") String size,
                                       @RequestParam("barcode") String barcode,
                                       @RequestParam("variantImage") MultipartFile variantImage,
                                       RedirectAttributes attributes) throws IOException, SQLException {
        ProductVariant productVariant = productVariantService.getById(productVariantId);
        if(productVariant != null) {
            productVariant.setSize(size);
            productVariant.setBarcode(barcode);
            Image oldImage = productVariant.getImage();
            Boolean deleteOldImage = false;
            if (variantImage != null && !variantImage.isEmpty()) {
                byte[] bytes = variantImage.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                Image image = new Image();
                image.setImage(blob);
                Image savedImage = imageService.create(image);
                productVariant.setImage(savedImage);
                deleteOldImage = true;
            }
            ProductVariant updatedProductVariant = productVariantService.update(productVariant);
            if (deleteOldImage) {
                imageService.delete(oldImage);
            }
            attributes.addFlashAttribute("successMessage", "Votre variante avec le code barre : " + productVariant.getBarcode() + " est modifiée avec succès");
            return "redirect:/product?productId="+updatedProductVariant.getProduct().getId();
        } else {
            attributes.addFlashAttribute("errorMessage", "La variante est introuvable!");
            return "redirect:/error";

        }
    }

    @GetMapping("/delete-variant")
    public String processDeleteVariant(
            @RequestParam("variantId") Long variantId,
            RedirectAttributes attributes
    ) {
        ProductVariant variant = productVariantService.getById(variantId);
        if(variant != null) {
            productVariantService.delete(variantId);
            if(variant.getImage() != null) {
                imageService.delete(variant.getImage());
            }
            attributes.addFlashAttribute("successMessage", "Votre variante : "+variant.getSize()+" est supprimée avec succès");
            return "redirect:/product?productId="+variant.getProduct().getId();
        }
        attributes.addFlashAttribute("errorMessage", "Variante introuvable!");
        return "redirect:/error";
    }

    @GetMapping("/show")
    public String show(ProductVariant productVariant, Model model) {
        model.addAttribute("variant", productVariant);
        return "showProductAfterUpdate";
    }
}
