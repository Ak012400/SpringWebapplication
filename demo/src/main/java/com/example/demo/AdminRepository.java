package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    boolean existsByUserIdAndPassword(String userId, String password);
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    Admin findByUserId(String userId);
    Admin findByEmail(String email);
}
