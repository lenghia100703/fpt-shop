package com.laptopshop.library.service;

import com.laptopshop.library.dto.CustomerDto;
import com.laptopshop.library.model.Brand;
import com.laptopshop.library.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

//    String register(CustomerDto customerDto);
    Customer save(CustomerDto customerDto);

    Customer findByUsername(String username);

    List<Customer> findALl();

    Customer update(CustomerDto customerDto);

    Customer changePass(CustomerDto customerDto);

    CustomerDto getCustomer(String username);

    Optional<Customer> findById(Long id);

    void deleteById(Long id);

    void enableById(Long id);

    boolean existsByUsername(String username);
}
