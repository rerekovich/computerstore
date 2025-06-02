package com.computerstore.service;
import com.computerstore.model.Product;
import com.computerstore.model.ProductCategory;
import com.computerstore.model.Sale;
import com.computerstore.repository.ProductRepository;
import com.computerstore.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SaleRepository saleRepository;
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    public Product updateProduct(Long id, Product updated) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        int oldQuantity = existing.getQuantityInStock();
        int newQuantity = updated.getQuantityInStock();
        existing.setQuantityInStock(newQuantity);
        Product savedProduct = productRepository.save(existing);
        int quantityChange = Math.abs(newQuantity - oldQuantity);
        if (quantityChange > 0) {
            Sale.OperationType operationType;
            BigDecimal operationPrice;
            if (newQuantity < oldQuantity) {
                operationType = Sale.OperationType.SALE;
                operationPrice = existing.getSalePrice();
            } else {
                operationType = Sale.OperationType.PURCHASE;
                operationPrice = existing.getPurchasePrice();
            }
            Sale sale = new Sale();
            sale.setProduct(savedProduct);
            sale.setQuantityChanged(quantityChange);
            sale.setSaleDate(LocalDate.now());
            sale.setOperationType(operationType);
            sale.setOperationPrice(operationPrice);
            saleRepository.save(sale);
        }
        return savedProduct;
    }
    public List<Product> getAll() {
        return productRepository.findAll();
    }
    public List<Product> getByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }
}