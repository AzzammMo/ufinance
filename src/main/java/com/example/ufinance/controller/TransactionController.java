package com.example.ufinance.controller;

import com.example.ufinance.model.Transaction;
import com.example.ufinance.model.Account;
import com.example.ufinance.repository.TransactionRepository;
import com.example.ufinance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    // POST /api/transactions
    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        if (transaction.getAccount() != null && transaction.getAccount().getId() != null) {
            Optional<Account> user = accountRepository.findById(transaction.getAccount().getId());
            if (user.isPresent()) {
                transaction.setAccount(user.get());
                Transaction saved = transactionRepository.save(transaction);
                return ResponseEntity.ok(saved);
            } else {
                return ResponseEntity.badRequest().body("Akun tidak ditemukan");
            }
        } else {
            return ResponseEntity.badRequest().body("Akun tidak boleh null");
        }
    }

    // GET /api/transactions/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable("userId") Integer userId) {
        List<Transaction> transactions = transactionRepository.findByAccount_Id(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/saldo/{userId}")
    public ResponseEntity<Map<String, Long>> getSaldo(@PathVariable Long userId) {
        Long saldo = transactionRepository.calculateSaldo(userId);
        if (saldo == null) saldo = 0L;

        Map<String, Long> response = new HashMap<>();
        response.put("saldo", saldo);
        return ResponseEntity.ok(response);
    }
    
}



    

