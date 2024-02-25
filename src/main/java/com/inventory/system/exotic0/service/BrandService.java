package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Brand;

import java.util.List;

public interface BrandService {

    public Brand save(Brand brand);
    public Brand getById(Long id);
    public List<Brand> findAll();
    public void delete(Brand brand);
}
