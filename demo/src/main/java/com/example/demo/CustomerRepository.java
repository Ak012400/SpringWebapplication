package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customers,Long> {
Customers findCustomersByUserId(String userId);
Customers findCustomersByCardNumber(Long cardNumber);
boolean   existsByCardNumber(Long cardNumber);
boolean    existsCustomersByUserId(String userId);
boolean    existsCustomersByEmail(String email);

}
