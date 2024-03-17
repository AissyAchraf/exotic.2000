package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    @Query("SELECT v FROM ProductVariant v WHERE v.barcode = :barcode")
    ProductVariant getByBarcode(@Param("barcode") String barcode);
}
