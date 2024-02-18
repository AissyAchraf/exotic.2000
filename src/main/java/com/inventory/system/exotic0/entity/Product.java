package com.inventory.system.exotic0.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends CatalogueElement {

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Category category;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;

    @Transient
    public List<Image> getVariantsImages() {
        List<Image> images =  new ArrayList<>();
        for (ProductVariant variant : this.variants) {
            if(variant.getImage() != null) {
                images.add(variant.getImage());
            }
        }
        return images;
    }

    @Transient
    public String getProductPathName() {
        String path = this.getName();

        if(this.category != null) {
            return this.category.getCategoryPathName() + " > " + path;
        } else {
            return this.getName();
        }
    }

    @Transient
    public boolean getIsInStock() {
        boolean inStock = false;
        if(variants != null) {
            for (ProductVariant variant : variants) {
                if(variant.getIsInStock()) {
                    inStock = true;
                    break;
                }
            }
        }
        return inStock;
    }

    @Transient
    public Float getMinSellingPrice() {
        if(variants != null && !variants.isEmpty()) {
            float minSellingPrice = variants.get(0).getMinSellingPrice();
            for (int i=1; i < variants.size(); i++) {
                if(variants.get(i).getMinSellingPrice() == null) {
                    continue;
                }
                if(minSellingPrice > variants.get(i).getMinSellingPrice()) {
                    minSellingPrice = variants.get(i).getMinSellingPrice();
                }
            }
            return minSellingPrice;
        }
        return null;
    }

    @Transient
    public List<StockSumByPriceRange> getStockSumByPriceRange() {
        List<StockSumByPriceRange> stockSumByPriceRangeList = new ArrayList<>();

        int[] priceRanges = {0, 100, 200, 300, Integer.MAX_VALUE};

        for (int i = 0; i < priceRanges.length - 1; i++) {
            stockSumByPriceRangeList.add(new StockSumByPriceRange(getRangeKey(priceRanges[i], priceRanges[i + 1]), 0));
        }

        for (ProductVariant variant : this.variants) {
            Float sellingPrice = variant.getMinSellingPrice();
            if(sellingPrice == null) {
                continue;
            }
            int rangeIndex = findPriceRangeIndex(priceRanges, sellingPrice);

            if (rangeIndex >= 0) {
                int currentSum = stockSumByPriceRangeList.get(rangeIndex).getStockSum();
                stockSumByPriceRangeList.get(rangeIndex).setStockSum(currentSum + variant.getTotalStockQuantity());
            }
        }

        return stockSumByPriceRangeList;
    }

    @Transient
    public int getNumberOfVariants() {
        if(variants == null) return 0;

        return this.variants.size();
    }

    private int findPriceRangeIndex(int[] priceRanges, Float sellingPrice) {
        for (int i = 0; i < priceRanges.length - 1; i++) {
            if (sellingPrice >= priceRanges[i] && sellingPrice < priceRanges[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    private String getRangeKey(int start, int end) {
        return start + "-" + end;
    }

    public static class StockSumByPriceRange {
        private String rangeKey;
        private int stockSum;

        public StockSumByPriceRange(String rangeKey, int stockSum) {
            this.rangeKey = rangeKey;
            this.stockSum = stockSum;
        }

        public String getRangeKey() {
            return rangeKey;
        }

        public int getStockSum() {
            return stockSum;
        }

        public void setStockSum(int stockSum) {
            this.stockSum = stockSum;
        }
    }
}
