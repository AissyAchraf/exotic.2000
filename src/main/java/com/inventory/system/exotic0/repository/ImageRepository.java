package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ImageRepository extends CrudRepository<Image, Long> {

}
