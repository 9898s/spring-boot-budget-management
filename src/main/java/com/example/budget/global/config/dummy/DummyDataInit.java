package com.example.budget.global.config.dummy;

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
  CommandLineRunner init(CategoryRepository categoryRepository, MemberRepository memberRepository) {
    return (args -> {
      categoryRepository.save(Category.builder().name("식비").build());
      categoryRepository.save(Category.builder().name("주거").build());
      categoryRepository.save(Category.builder().name("교통").build());

      memberRepository.save(Member.builder().account("kim").password("123").build());
      memberRepository.save(Member.builder().account("su").password("123").build());
      memberRepository.save(Member.builder().account("hwan").password("123").build());
    });
  }
}
