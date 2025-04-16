package com.example.finding_spare_part.repo;

import com.example.finding_spare_part.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    int deleteByEmail(String email);
    List<User> findByRole(String role);
}