package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.ProductVariant;

public interface ProductVariantService {

    public ProductVariant create(ProductVariant productVariant);
    public void delete(Long id);
    public ProductVariant update(ProductVariant productVariant);
    public ProductVariant getById(Long id);
}
