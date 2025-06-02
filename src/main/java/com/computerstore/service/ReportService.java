package com.computerstore.service;
import com.computerstore.model.ProductCategory;
import com.computerstore.model.Sale;
import com.computerstore.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    private SaleRepository saleRepository;
    public int getTotalQuantitySold(LocalDate from, LocalDate to) {
        List<Sale> sales = saleRepository.findBySaleDateBetween(from, to);
        return sales.stream()
                .filter(sale -> sale.getOperationType() == Sale.OperationType.SALE)
                .mapToInt(Sale::getQuantityChanged)
                .sum();
    }
    public Map<ProductCategory, BigDecimal> getProfitByCategory(LocalDate from, LocalDate to) {
        List<Sale> sales = saleRepository.findBySaleDateBetween(from, to);
        Map<ProductCategory, BigDecimal> salesSumByCategory = sales.stream()
                .filter(sale -> sale.getOperationType() == Sale.OperationType.SALE)
                .collect(Collectors.groupingBy(
                        sale -> sale.getProduct().getCategory(),
                        Collectors.mapping(
                                sale -> sale.getOperationPrice().multiply(BigDecimal.valueOf(sale.getQuantityChanged())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        Map<ProductCategory, BigDecimal> purchaseSumByCategory = sales.stream()
                .filter(sale -> sale.getOperationType() == Sale.OperationType.PURCHASE)
                .collect(Collectors.groupingBy(
                        sale -> sale.getProduct().getCategory(),
                        Collectors.mapping(
                                sale -> sale.getOperationPrice().multiply(BigDecimal.valueOf(sale.getQuantityChanged())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        return salesSumByCategory.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().subtract(purchaseSumByCategory.getOrDefault(e.getKey(), BigDecimal.ZERO))
                ));
    }
}
