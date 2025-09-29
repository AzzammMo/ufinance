package com.example.ufinance.repository;

import com.example.ufinance.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByAccount_Id(Integer accountId); 
}

