package com.example.finding_spare_part.repo;


import com.example.finding_spare_part.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    int deleteByEmail(String email);
}
