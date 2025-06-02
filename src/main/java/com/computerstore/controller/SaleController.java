package com.computerstore.controller;
import com.computerstore.model.Sale;
import com.computerstore.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin
public class SaleController {
    @Autowired
    private SaleService saleService;
    @PostMapping
    public Sale recordSale(@RequestParam Long productId, @RequestParam Integer quantity) {
        return saleService.recordSale(productId, quantity);
    }
}