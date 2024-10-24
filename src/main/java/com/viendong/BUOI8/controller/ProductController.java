package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.Category;
import org.springframework.ui.Model;
import com.viendong.BUOI8.model.Product;
import com.viendong.BUOI8.service.CategoryService;
import com.viendong.BUOI8.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;// Đảm bảo bạn đã inject CategoryService
// Display a list of all products




    @GetMapping("/admin")
    public String showProducts() {
//        model.addAttribute("products",productService.getAllProducts());
        return "/admin/index";
    }


    @GetMapping("/products/products-list")
    public String showProduct(Model model)
    {
        model.addAttribute("products",productService.getAllProducts());
        return "/products/products-list";
    }



    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories",categoryService.getAllCategories());// Loadcategories
        return "/products/add-product";
    }
    // Process the form for adding a new product
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult bindingResult,
                             @RequestParam("image") MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return "products/add-product";
        }

        // Kiểm tra nếu ảnh không rỗng
        if (image != null && !image.isEmpty()) {
            // Lưu hình ảnh
            String imageUrl = saveImage(image);
            product.setImageUrl(imageUrl);
        }

        productService.addProduct(product);
        return "redirect:/products/products-list";
    }
    // For editing a product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories",categoryService.getAllCategories());//Loadcategories
        return "/products/update-product";
    }
    // Process the form for updating a product
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid Product product,
                                BindingResult bindingResult,
                                @RequestParam(value = "image", required = false) MultipartFile image) {
        if (bindingResult.hasErrors()) {
            product.setId(id);
            return "products/update-product";
        }

        if (!image.isEmpty()) {
            String imageUrl = saveImage(image);
            product.setImageUrl(imageUrl);
        }

        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/products/products-list";
    }
    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products/products-list";
    }

    // Một phương thức để lưu hình ảnh (có thể lưu vào hệ thống file hoặc vào một dịch vụ lưu trữ)
    private String saveImage(MultipartFile image) {
        // Đường dẫn lưu tệp
        String uploadDir = "D:/workspaceJAVAUP/BUOI12 - Copy/src/main/resources/static/uploads/";
        try {
            // Tạo thư mục nếu chưa tồn tại
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Lưu tệp hình ảnh
            String filePath = uploadDir + image.getOriginalFilename();
            image.transferTo(new File(filePath));

            // Trả về đường dẫn truy cập hình ảnh
            return "/uploads/" + image.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ theo nhu cầu
            return null;
        }
    }
}
