package com.example.order_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;


@Entity
public class Product {

    public Product() {
    }

    public Product(Integer id, String name, BigDecimal price, Integer stockQty) {
        this.name = name;
        this.price = price;
        this.stockQty = stockQty;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private BigDecimal price;

    private Integer stockQty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    @Override
    public String toString() {
        return "Product:" +
                " id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock_Qty=" + stockQty ;
    }
}
