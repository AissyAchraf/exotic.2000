package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.ProductVariant;
import com.inventory.system.exotic0.entity.Stock;
import com.inventory.system.exotic0.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock getById(Long id) {
        return stockRepository.getById(id);
    }

    @Override
    public void delete(Stock stock) {
        stockRepository.delete(stock);
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public List<Stock> findByProductVariant(ProductVariant productVariant) {
        return stockRepository.findAllByProductVariant(productVariant);
    }
}
