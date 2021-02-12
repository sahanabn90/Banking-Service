package com.demo.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.bankapp.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
