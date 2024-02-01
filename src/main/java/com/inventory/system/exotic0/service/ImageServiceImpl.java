package com.inventory.system.exotic0.service;

import com.inventory.system.exotic0.entity.Image;
import com.inventory.system.exotic0.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }
    @Override
    public List<Image> viewAll() {
        return (List<Image>) imageRepository.findAll();
    }
    @Override
    public Image viewById(Long id) {
        return imageRepository.findById(id).get();
    }
    @Override
    public void delete(Image image) {
        imageRepository.delete(image);
    }
}
