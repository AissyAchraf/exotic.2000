package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.ProductVariant;
import com.inventory.system.exotic0.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.productVariant = :productVariant")
    List<Stock> findAllByProductVariant(@Param("productVariant") ProductVariant productVariant);
}
