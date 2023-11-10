package com.example.budget.budget.repository;

import com.example.budget.budget.entity.Budget;
import com.example.budget.category.entity.Category;
import com.example.budget.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

  boolean existsByMemberAndCategory(Member member, Category category);

  @Query("SELECT b FROM Budget b JOIN FETCH b.category")
  List<Budget> findAllWithCategory();
}
