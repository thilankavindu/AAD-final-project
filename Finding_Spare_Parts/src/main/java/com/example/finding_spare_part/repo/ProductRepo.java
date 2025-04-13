package com.example.finding_spare_part.repo;
//
//import com.example.finding_spare_part.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ProductRepo extends JpaRepository<Product, Long> {
//}

import com.example.finding_spare_part.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}