package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}
