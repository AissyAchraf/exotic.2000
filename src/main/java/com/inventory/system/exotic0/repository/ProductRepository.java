package com.inventory.system.exotic0.repository;

import com.inventory.system.exotic0.entity.Category;
import com.inventory.system.exotic0.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.name = :name")
    public Page search(@Param("name") String name, Pageable pageable);

    @Query("select p from Product p where p.category.id = :categoryId")
    public Page searchByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category = :category OR p.category.parent = :category")
    List<Product> findProductsByCategoryAndSubcategories(@Param("category") Category category);

    @Query(value = "WITH RECURSIVE CategoryTree AS ( " +
            "  SELECT id, parent_id " +
            "  FROM category " +
            "  WHERE id = :categoryId " +
            "  UNION " +
            "  SELECT c.id, c.parent_id " +
            "  FROM category c " +
            "  JOIN CategoryTree ct ON c.parent_id = ct.id) " +
            "SELECT p FROM Product p " +
            "WHERE p.category IN (SELECT id FROM CategoryTree)", nativeQuery = true)
    Page<Product> findAllByCategoryAndSubCategories(@Param("categoryId") Long categoryId, Pageable pageable);

    List<Product> findProductsByCategory(Category category);

}
