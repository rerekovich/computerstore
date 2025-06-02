package com.computerstore.service;
import com.computerstore.model.Product;
import com.computerstore.model.Sale;
import com.computerstore.repository.ProductRepository;
import com.computerstore.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    public Sale recordSale(Long productId, Integer newQuantityInStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        int oldQuantity = product.getQuantityInStock();
        if (newQuantityInStock > oldQuantity) {
            throw new RuntimeException("Cannot increase stock in a sale operation");
        }
        int quantitySold = oldQuantity - newQuantityInStock;
        product.setQuantityInStock(newQuantityInStock);
        productRepository.save(product);
        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setQuantityChanged(quantitySold);
        sale.setSaleDate(LocalDate.now());
        return saleRepository.save(sale);
    }
    public List<Sale> getSalesBetween(LocalDate start, LocalDate end) {
        return saleRepository.findBySaleDateBetween(start, end);
    }
}