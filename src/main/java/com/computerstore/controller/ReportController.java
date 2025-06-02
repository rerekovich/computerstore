package com.computerstore.controller;
import com.computerstore.model.ProductCategory;
import com.computerstore.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/quantity-sold")
    public int getTotalQuantitySold(@RequestParam String from, @RequestParam String to) {
        return reportService.getTotalQuantitySold(LocalDate.parse(from), LocalDate.parse(to));
    }
    @GetMapping("/profit-by-category")
    public Map<ProductCategory, BigDecimal> getProfitByCategory(
            @RequestParam String from,
            @RequestParam String to) {
        return reportService.getProfitByCategory(LocalDate.parse(from), LocalDate.parse(to));
    }
}
