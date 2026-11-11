/* Product.java
Product POJO
Author: Zacharia Dipudi (230700659)
Date: 21 June 2026*/
package za.ac.cput.redesigned_store_inventory_manager.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    private String productId;
    private String productName;
    private String category;


    public Product(){

    }
    private Product(Builder builder){
        this.productId= builder.productId;
        this.productName=builder.productName;
        this.category=builder.category;

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public static class Builder{
        private String productId;
        private String productName;
        private String category;

        public Builder copy(Product product){
            this.productId=product.productId;
            this.productName=product.productName;
            this.category=product.category;
            return this;
        }

        public Builder setProductId(String productId){
            this.productId=productId;
            return this;
        }
        public Builder setProductName(String productName){
            this.productName=productName;
            return this;
        }
        public Builder setCategory(String category){
            this.category=category;
            return this;
        }
        public Product build(){
            return new Product(this);
        }
    }

}