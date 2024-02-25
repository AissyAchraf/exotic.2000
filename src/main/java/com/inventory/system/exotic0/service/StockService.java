package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.ProductVariant;
import com.inventory.system.exotic0.entity.Stock;

import java.util.List;

public interface StockService {

    public Stock save(Stock stock);
    public Stock getById(Long id);
    public void delete(Stock stock);
    public List<Stock> findAll();
    public List<Stock> findByProductVariant(ProductVariant productVariant);
}
