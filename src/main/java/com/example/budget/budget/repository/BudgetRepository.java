package com.example.budget.budget.repository;

import com.example.budget.budget.entity.Budget;
import com.example.budget.category.entity.Category;
import com.example.budget.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

  boolean existsByMemberAndCategory(Member member, Category category);
}
