package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ImageRepository extends CrudRepository<Image, Long> {

}
