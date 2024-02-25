package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.ProductVariant;
import com.inventory.system.exotic0.entity.Stock;
import com.inventory.system.exotic0.service.ProductVariantService;
import com.inventory.system.exotic0.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;
    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping("/stock")
    public String viewProductVariantStocks(
            Model model,
            RedirectAttributes attributes,
            @RequestParam("variantId") Long variantId) {
        ProductVariant productVariant = productVariantService.getById(variantId);
        if(productVariant == null) {
            attributes.addFlashAttribute("errorMessage", "Aucune variante n'est trouvé avec l'identifiant :"+variantId);
            return "redirect:/error";
        }
        List<Stock> stockList = stockService.findByProductVariant(productVariant);
        model.addAttribute("productVariant", productVariant);
        model.addAttribute("stockList", stockList);
        if(!model.containsAttribute("newStock")) {
            model.addAttribute("newStock", new Stock());
        }
        return "Stocks/variantStock";
    }

    @PostMapping("/create-stock")
    public String processCreateStock(
            Stock stock,
            RedirectAttributes attributes,
            @RequestParam("variantId") Long variantId
    ) {
        ProductVariant productVariant = productVariantService.getById(variantId);
        if(productVariant == null) {
            attributes.addFlashAttribute("errorMessage", "Aucune variante n'est trouvé avec l'identifiant :"+variantId);
            return "redirect:/error";
        }
        stock.setProductVariant(productVariant);
        stock.setCurrentQuantity(stock.getQuantity());
        Stock savedStock = stockService.save(stock);
        attributes.addFlashAttribute("successMessage", "Un nouveau stock a été ajouter avec succès !");
        return "redirect:/stock?variantId="+variantId;
    }

    @PostMapping("/update-stock")
    public String processUpdateStock(
            Stock stock,
            RedirectAttributes attributes,
            @RequestParam("variantId") Long variantId
    ) {
        ProductVariant productVariant = productVariantService.getById(variantId);
        if(productVariant == null) {
            attributes.addFlashAttribute("errorMessage", "Aucune variante n'est trouvé avec l'identifiant :"+variantId);
            return "redirect:/error";
        }
        stock.setProductVariant(productVariant);
        Stock savedStock = stockService.save(stock);
        attributes.addFlashAttribute("successMessage", "Le stock a été modifié avec succès !");
        return "redirect:/stock?variantId="+variantId;
    }
}
