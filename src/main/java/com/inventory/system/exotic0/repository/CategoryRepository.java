package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.name like :name")
    public Page search(@Param("name") String name, Pageable pageable);

    @Query("select c from Category c where c.parent is null")
    public List<Category> findRootCategories();

    @Query("select c from Category c where c.parent is null")
    public Page searchRootCategories(@Param("name") String name, Pageable pageable);

    @Query("select c from Category c where c.parent.id = :parentId")
    public Page searchByParentId(@Param("parentId") Long parentId, Pageable pageable);
}
