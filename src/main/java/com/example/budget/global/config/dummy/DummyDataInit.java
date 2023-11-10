package com.example.budget.global.config.dummy;

import com.example.budget.budget.entity.Budget;
import com.example.budget.budget.repository.BudgetRepository;
import com.example.budget.category.entity.Category;
import com.example.budget.category.repository.CategoryRepository;
import com.example.budget.member.entity.Member;
import com.example.budget.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyDataInit {

  @Bean
  CommandLineRunner init(
      CategoryRepository categoryRepository,
      MemberRepository memberRepository,
      BudgetRepository budgetRepository
  ) {
    return (args -> {
      // 카테고리
      Category food = categoryRepository.save(Category.builder().name("식비").build());
      Category home = categoryRepository.save(Category.builder().name("주거").build());
      Category traffic = categoryRepository.save(Category.builder().name("교통").build());

      // 회원
      Member a = memberRepository.save(Member.builder().account("a").password("123").build());
      Member b = memberRepository.save(Member.builder().account("b").password("123").build());
      Member c = memberRepository.save(Member.builder().account("c").password("123").build());
      Member d = memberRepository.save(Member.builder().account("d").password("123").build());
      Member e = memberRepository.save(Member.builder().account("e").password("123").build());
      Member f = memberRepository.save(Member.builder().account("f").password("123").build());
      Member g = memberRepository.save(Member.builder().account("g").password("123").build());
      Member h = memberRepository.save(Member.builder().account("h").password("123").build());
      Member i = memberRepository.save(Member.builder().account("i").password("123").build());
      Member j = memberRepository.save(Member.builder().account("j").password("123").build());

      // 예산 - 식비
      budgetRepository.save(Budget.builder().amount(100L).member(a).category(food).build());
      budgetRepository.save(Budget.builder().amount(100L).member(b).category(food).build());
      budgetRepository.save(Budget.builder().amount(100L).member(c).category(food).build());
      budgetRepository.save(Budget.builder().amount(100L).member(d).category(food).build());
      budgetRepository.save(Budget.builder().amount(100L).member(e).category(food).build());

      // 예산 - 주거
      budgetRepository.save(Budget.builder().amount(100L).member(f).category(home).build());
      budgetRepository.save(Budget.builder().amount(100L).member(g).category(home).build());
      budgetRepository.save(Budget.builder().amount(100L).member(h).category(home).build());
      budgetRepository.save(Budget.builder().amount(100L).member(i).category(home).build());

      // 예산 - 교통
      budgetRepository.save(Budget.builder().amount(100L).member(j).category(traffic).build());
    });
  }
}
