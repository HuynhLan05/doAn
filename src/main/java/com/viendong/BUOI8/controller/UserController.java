package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.User;
import com.viendong.BUOI8.service.CategoryService;
import com.viendong.BUOI8.service.ProductService;
import com.viendong.BUOI8.service.UserService;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller // Đánh dấu lớp này là một Controller trong Spring MVC.
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;



    @GetMapping("/users")
    public String showUserList(Model model) {
//        model.addAttribute("products",productService.getAllProducts());
        return "users/index";// Return the name of the view template, e.g., user-list.html
    }

    @GetMapping("/users/about")
    public String about() {
        return "users/about"; // This will return about.html in templates folder
    }

    @GetMapping("/users/product")
    public String product() {
        return "users/product"; // This will return about.html in templates folder
    }

    @GetMapping("/users/blog")
    public String blog() {
        return "users/blog"; // This will return about.html in templates folder
    }

    @GetMapping("/users/feature")
    public String feature() {
        return "users/feature"; // This will return about.html in templates folder
    }

    @GetMapping("/users/testimonial")
    public String testimonial() {
        return "users/testimonial"; // This will return about.html in templates folder
    }

    @GetMapping("/users/404")
    public String page_error() {
        return "users/404"; // This will return about.html in templates folder
    }

    @GetMapping("/users/contact")
    public String contact() {
        return "users/contact"; // This will return about.html in templates folder
    }







    @GetMapping("/login")
    public String login() {
        return "users/login";
    }
    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new User()); // Thêm một đối tượng User mới vào model
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, //Validate đối tượng User
                           @NotNull BindingResult bindingResult, // Kết quả của quá trình validate
                                   Model model) {
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors =
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "users/register"; // Trả về lại view "register" nếu có lỗi
        }
        userService.save(user); // Lưu người dùng vào cơ sở dữ liệu
        userService.setDefaultRole(user.getUsername()); // Gán vai trò mặc định cho người dùng
        return "redirect:/login"; // Chuyển hướng người dùng tới trang "login"
    }


}