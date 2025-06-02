package com.computerstore.controller;
import com.computerstore.model.Product;
import com.computerstore.model.ProductCategory;
import com.computerstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping
    public Product add(@RequestBody Product product) {
        return productService.addProduct(product);
    }
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }
    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable ProductCategory category) {
        return productService.getByCategory(category);
    }
}