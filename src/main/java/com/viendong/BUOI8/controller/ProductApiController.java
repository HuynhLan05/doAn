package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.Product;
import com.viendong.BUOI8.service.CategoryService;
import com.viendong.BUOI8.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Fetch all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Fetch product by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add new product
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestParam("name") String name,
                                        @RequestParam("price") double price,
                                        @RequestParam("description") String description,
                                        @RequestParam("category") Long categoryId,
                                        @RequestParam("image") MultipartFile image) {
        // Kiểm tra nếu danh mục không tồn tại
        var categoryOpt = categoryService.getCategoryById(categoryId);
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found");
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(categoryOpt.get());

        // Lưu ảnh và gán đường dẫn
        String imageUrl = saveImage(image);
        if (imageUrl != null) {
            product.setImageUrl(imageUrl);
        }

        productService.addProduct(product);
        return ResponseEntity.ok(product);
    }

    // Update existing product
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestParam("name") String name,
                                           @RequestParam("price") double price,
                                           @RequestParam("description") String description,
                                           @RequestParam("category") Long categoryId,
                                           @RequestParam(value = "image", required = false) MultipartFile image) {

        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));

        // Kiểm tra nếu danh mục không tồn tại
        var categoryOpt = categoryService.getCategoryById(categoryId);
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found");
        }

        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(categoryOpt.get());

        // Nếu có ảnh mới, lưu ảnh mới và cập nhật đường dẫn
        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            if (imageUrl != null) {
                product.setImageUrl(imageUrl);
            }
        }

        productService.updateProduct(product);
        return ResponseEntity.ok(product);
    }


    // Delete a product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    // Helper method to save images
    private String saveImage(MultipartFile image) {
        String uploadDir = "D:/workspaceJAVAUP/BUOI12 - Copy/src/main/resources/static/uploads/";
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = uploadDir + image.getOriginalFilename();
            image.transferTo(new File(filePath));
            return "/uploads/" + image.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
