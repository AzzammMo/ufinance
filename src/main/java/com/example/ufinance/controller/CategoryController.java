package com.example.ufinance.controller;

import com.example.ufinance.model.Category;
import com.example.ufinance.model.Account;
import com.example.ufinance.repository.CategoryRepository;
import com.example.ufinance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    // ✅ Perbaiki penamaan parameter
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestParam("userId") Integer userId, @RequestBody Category category) {
        Optional<Account> user = accountRepository.findById(userId);
        if (user.isPresent()) {
            category.setAccount(user.get());
            Category saved = categoryRepository.save(category);
            return ResponseEntity.ok(saved);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User tidak ditemukan");
        }
    }

    @GetMapping("/{userId}")
    public List<Category> getCategories(@PathVariable("userId") Integer userId) {
        return categoryRepository.findByAccount_Id(userId);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
            return ResponseEntity.ok("Kategori berhasil dihapus");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kategori tidak ditemukan");
        }
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable("categoryId") Integer categoryId,
            @RequestBody Category updatedCategory) {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setCategoryName(updatedCategory.getCategoryName());
            return categoryRepository.save(category);
        }).orElse(null);
    }
}