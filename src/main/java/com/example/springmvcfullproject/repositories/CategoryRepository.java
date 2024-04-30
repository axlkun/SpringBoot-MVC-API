package com.example.springmvcfullproject.repositories;

import com.example.springmvcfullproject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
