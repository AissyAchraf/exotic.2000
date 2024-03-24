package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Image;
import com.inventory.system.exotic0.entity.ProductVariant;
import com.inventory.system.exotic0.repository.ImageRepository;
import com.inventory.system.exotic0.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ProductVariant create(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);
    }

    @Override
    public void delete(Long id) {
        productVariantRepository.deleteById(id);
    }

    @Override
    public ProductVariant update(ProductVariant productVariant) {
        ProductVariant updatedVariant = productVariantRepository.save(productVariant);
        return updatedVariant;
    }

    @Override
    public ProductVariant getById(Long id) {
        return productVariantRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductVariant> findAll() {
        return productVariantRepository.findAll();
    }

    @Override
    public ProductVariant getByBarcode(String barcode) {
        return productVariantRepository.getByBarcode(barcode);
    }
}
