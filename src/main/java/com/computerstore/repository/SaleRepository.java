package com.computerstore.repository;
import com.computerstore.model.Sale;
import com.computerstore.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySaleDateBetween(LocalDate start, LocalDate end);
    @Query("SELECT s FROM Sale s WHERE s.product.category = :category AND s.saleDate BETWEEN :start AND :end")
    List<Sale> findByCategory(ProductCategory category);
}