package com.enigma.livecode_bioskop.service;

import java.util.List;

import com.enigma.livecode_bioskop.models.entity.Customer;

public interface CustomerService {
    public Customer createCustomer(Customer customer);
    Customer findCustomerById(Integer id);
    List<Customer> getAllCustomer();
    void updateCustomer(Customer customer);
    void deleteCustomerByid(Integer id);
    // Page<Customer> getCustomerPerPage(String name,Pageable pageable);
}

