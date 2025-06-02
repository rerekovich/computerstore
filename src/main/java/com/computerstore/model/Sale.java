package com.computerstore.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Sale {
    public enum OperationType {
        SALE, PURCHASE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    private Integer quantityChanged;
    private LocalDate saleDate;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private BigDecimal operationPrice;
    public Sale() {}
    public Sale(Product product, Integer quantityChanged, LocalDate saleDate, OperationType operationType, BigDecimal operationPrice) {
        this.product = product;
        this.quantityChanged = quantityChanged;
        this.saleDate = saleDate;
        this.operationType = operationType;
        this.operationPrice = operationPrice;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Integer getQuantityChanged() {
        return quantityChanged;
    }
    public void setQuantityChanged(Integer quantityChanged) {
        this.quantityChanged = quantityChanged;
    }
    public LocalDate getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }
    public OperationType getOperationType() {
        return operationType;
    }
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
    public BigDecimal getOperationPrice() {
        return operationPrice;
    }
    public void setOperationPrice(BigDecimal operationPrice) {
        this.operationPrice = operationPrice;
    }
}
