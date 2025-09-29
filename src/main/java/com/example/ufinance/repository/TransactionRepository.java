package com.example.ufinance.repository;

import com.example.ufinance.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    
    List<Transaction> findByAccount_Id(Integer accountId);
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByAccountId(Integer accountId);
    

    @Query("SELECT SUM(CASE WHEN t.type = 'income' THEN t.amount ELSE -t.amount END) FROM Transaction t WHERE t.account.id = :accountId")
    Long calculateSaldo(@Param("accountId") Long accountId);

    
    
}
