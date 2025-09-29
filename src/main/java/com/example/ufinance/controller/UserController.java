package com.example.ufinance.controller;

import com.example.ufinance.model.Account;
import com.example.ufinance.model.Transaction;
import com.example.ufinance.repository.AccountRepository;
import com.example.ufinance.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/signup")
    public Account signup(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Account loginUser) {
        Account account = accountRepository.findByUsernameAndPasswordAndEmail(
                loginUser.getUsername(),
                loginUser.getPassword(),
                loginUser.getEmail());

        if (account != null) {
            // Buat response JSON manual
            Map<String, Object> response = new HashMap<>();
            response.put("userId", account.getId()); // agar cocok dengan frontend
            response.put("username", account.getUsername());
            response.put("email", account.getEmail());

            return ResponseEntity.ok(response); // kode 200
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }

    @PostMapping("/forgot-password")
    public Account forgotPassword(@RequestParam String email, @RequestParam String newPassword) {
        Account account = accountRepository.findByEmail(email);
        if (account != null) {
            account.setPassword(newPassword);
            return accountRepository.save(account);
        }
        return null;
    }

    @GetMapping("/info/{userId}")
    public Account getUserInfo(@PathVariable Integer userId) {
        return accountRepository.findById(userId).orElse(null);
    }

    @PutMapping("/edit/{userId}")
    public Account updateUser(@PathVariable Integer userId, @RequestBody Account updatedAccount) {
        Optional<Account> optionalAccount = accountRepository.findById(userId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setUsername(updatedAccount.getUsername());
            account.setEmail(updatedAccount.getEmail());
            account.setPassword(updatedAccount.getPassword());
            return accountRepository.save(account);
        }
        return null;
    }

    @GetMapping("/saldo/{userId}")
public ResponseEntity<Map<String, Object>> getSummary(@PathVariable("userId") Integer userId) {
    List<Transaction> transactions = transactionRepository.findByAccountId(userId);

    double totalIncome = 0;
    double totalExpense = 0;

    for (Transaction tx : transactions) {
        if ("income".equalsIgnoreCase(tx.getType())) {
            totalIncome += tx.getAmount();
        } else if ("expense".equalsIgnoreCase(tx.getType())) {
            totalExpense += tx.getAmount();
        }
    }

    double saldo = totalIncome - totalExpense;
    int totalTransactions = transactions.size();

    Map<String, Object> response = new HashMap<>();
    response.put("totalIncome", totalIncome);
    response.put("totalExpense", totalExpense);
    response.put("saldo", saldo);
    response.put("totalTransactions", totalTransactions);

    return ResponseEntity.ok(response);
}

    @GetMapping("/{id}")
    public ResponseEntity<Account> getUserById(@PathVariable Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
