package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.Brand;
import com.inventory.system.exotic0.entity.Category;
import com.inventory.system.exotic0.entity.Image;
import com.inventory.system.exotic0.service.BrandService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/brands")
    public String index(Model model) {
        List<Brand> brandList = brandService.findAll();
        if(!model.containsAttribute("newBrand")) {
            model.addAttribute("newBrand", new Brand());
        }
        model.addAttribute("brandList", brandList);
        return "Brands/search";
    }

    @PostMapping("/create-brand")
    public String processCreateBrand(
            Brand brand,
            RedirectAttributes attributes
    ) throws SQLException, IOException {
        Brand savedBrand = brandService.save(brand);
        attributes.addFlashAttribute("successMessage", "Votre Marque : "+brand.getName()+" est ajoutée avec succès");
        return "redirect:/brands";
    }

    @PostMapping("/update-brand")
    public String processUpdateCategory(
            @RequestParam("brandId") Long brandId,
            @RequestParam("name") String name,
            RedirectAttributes attributes
    ) throws IOException, SQLException {
        Brand brand = brandService.getById(brandId);

        if(brand != null)  {
            brand.setName(name);

            brandService.save(brand);

            attributes.addFlashAttribute("successMessage", "Votre Marque : "+brand.getName()+" est modifiée avec succès");
            return "redirect:/brands";
        }
        attributes.addFlashAttribute("errorMessage", "La marque est introuvable!");
        return "redirect:/brands";
    }

    @GetMapping("/delete-brand")
    public String processDeleteBrand(
            @RequestParam("brandId") Long brandId,
            RedirectAttributes attributes
    ) throws IOException, SQLException {
        Brand brand = brandService.getById(brandId);

        if(brand != null)  {

            brandService.delete(brand);

            attributes.addFlashAttribute("successMessage", "Votre Marque : "+brand.getName()+" est supprimée avec succès");
            return "redirect:/brands";
        }
        attributes.addFlashAttribute("errorMessage", "La marque est introuvable!");
        return "redirect:/brands";
    }
}
