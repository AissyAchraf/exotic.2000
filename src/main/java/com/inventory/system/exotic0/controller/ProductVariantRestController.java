package com.inventory.system.exotic0.controller;

import com.inventory.system.exotic0.entity.ProductVariant;
import com.inventory.system.exotic0.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/productVariant")
public class ProductVariantRestController {

    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping("/getByBarcode/{barcode}")
    public ProductVariant getByBarcode(@PathVariable String barcode) {
        return productVariantService.getByBarcode(barcode);
    }
}
