package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
