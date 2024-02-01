package com.inventory.system.exotic0.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category extends CatalogueElement {


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "parent_id")
    private List<Category> components;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "category_id")
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Transient
    public String getCategoryPathName() {
        String path = this.getName();

        if(this.parent != null) {
            return this.parent.getCategoryPathName() + " > " + path;
        } else {
            return this.getName();
        }
    }

    @Transient
    public List<ProductCountByPriceRange> getTotalProductCountByPriceRange() {
        List<ProductCountByPriceRange> totalProductCountByPriceRangeList = new ArrayList<>();

        int[] priceRanges = {0, 100, 200, 300, Integer.MAX_VALUE};

        for (int i = 0; i < priceRanges.length - 1; i++) {
            totalProductCountByPriceRangeList.add(new ProductCountByPriceRange(i, 0));
        }

        processCategoryRecursively(this, totalProductCountByPriceRangeList, priceRanges);

        return totalProductCountByPriceRangeList;
    }

    private void processCategoryRecursively(Category category, List<ProductCountByPriceRange> result, int[] priceRanges) {
        if (category.getProducts() != null) {
            for (Product product : category.getProducts()) {
                List<Product.StockSumByPriceRange> stockByPriceRange = product.getStockSumByPriceRange();

                for (int i = 0; i < priceRanges.length - 1; i++) {
                    int currentCount = result.get(i).getProductCount();
                    result.get(i).setProductCount(currentCount + stockByPriceRange.get(i).getStockSum());
                }
            }
        }

        if (category.getComponents() != null && !category.components.isEmpty()) {
            for (Category subCategory : category.getComponents()) {
                processCategoryRecursively(subCategory, result, priceRanges);
            }
        }
    }

    @Transient
    public int getTotalProductCount() {
        int totalProductCount = 0;

        totalProductCount += processCategoryRecursively(this);

        return totalProductCount;
    }

    private int processCategoryRecursively(Category category) {
        int productCount = 0;

        if (category.getProducts() != null) {
            productCount += category.getProducts().size();
        }

        if (category.getComponents() != null) {
            for (Category subCategory : category.getComponents()) {
                productCount += processCategoryRecursively(subCategory);
            }
        }

        return productCount;
    }

    public static class ProductCountByPriceRange {
        private int rangeKey;
        private int productCount;

        public ProductCountByPriceRange(int rangeKey, int productCount) {
            this.rangeKey = rangeKey;
            this.productCount = productCount;
        }

        public int getRangeKey() {
            return rangeKey;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }
    }

    private int getRangeKey(int start, int end) {
        return start / 100;
    }
}
