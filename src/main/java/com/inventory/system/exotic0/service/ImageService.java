package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    public Image create(Image image);
    public List<Image> viewAll();
    public Image viewById(Long id);
    public void delete(Image image);
}
