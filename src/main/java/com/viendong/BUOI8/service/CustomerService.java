package com.viendong.BUOI8.service;

import com.viendong.BUOI8.model.Customer;
import com.viendong.BUOI8.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer addCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        customer.setId(id);
        customerRepository.save(customer);
        return customer;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
