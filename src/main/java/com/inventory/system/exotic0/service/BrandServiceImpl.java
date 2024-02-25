package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Brand;
import com.inventory.system.exotic0.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.getById(id);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public void delete(Brand brand) {
        brandRepository.delete(brand);
    }
}
