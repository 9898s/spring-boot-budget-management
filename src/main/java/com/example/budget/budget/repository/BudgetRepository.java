package com.example.budget.budget.repository;

import com.example.budget.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

}
