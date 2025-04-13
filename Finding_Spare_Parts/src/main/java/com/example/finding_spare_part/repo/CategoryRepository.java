package com.example.finding_spare_part.repo;

import com.example.finding_spare_part.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
