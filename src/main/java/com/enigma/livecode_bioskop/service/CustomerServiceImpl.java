package com.enigma.livecode_bioskop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.livecode_bioskop.models.entity.Customer;
import com.enigma.livecode_bioskop.models.repos.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerById(Integer id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public void updateCustomer(Customer customer) {
        this.customerRepository.findById(customer.getId()).get();
        this.customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerByid(Integer id) {
        this.customerRepository.deleteById(id);
    }

    
}
