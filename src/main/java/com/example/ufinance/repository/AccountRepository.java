package com.example.ufinance.repository;

import com.example.ufinance.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsernameAndPasswordAndEmail(String username, String password, String email);
    Account findByEmail(String email);
}
