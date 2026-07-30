package com.example.order_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderItem {

    public OrderItem() {
    }

    public OrderItem(Product product, Order order, Integer quantity, BigDecimal priceAtOrder) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    private Integer quantity;

    private BigDecimal priceAtOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(BigDecimal priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    @Override
    public String toString() {
        return "OrderItem " +
                "id=" + id +
                ", product=" + product +
                ", order=" + order +
                ", quantity=" + quantity +
                ", priceAtOrder=" + priceAtOrder;
    }
}
