package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.Customer;
import com.viendong.BUOI8.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "/customer/customer_list";  // Trang danh sách khách hàng
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("isEdit", false);
        return "/customer/customer_form";  // Trang thêm khách hàng
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.addCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("isEdit", true);
        return "/customer/customer_form";  // Đảm bảo rằng customer_form.html tồn tại trong templates
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, @ModelAttribute Customer customer) {
        customerService.updateCustomer(id, customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
