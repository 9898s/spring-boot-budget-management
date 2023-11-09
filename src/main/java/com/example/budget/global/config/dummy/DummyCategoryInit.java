package com.example.budget.global.config.dummy;

import com.example.budget.category.entity.Category;
import com.example.budget.category.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyCategoryInit {

  @Bean
  CommandLineRunner init(CategoryRepository categoryRepository) {
    return (args -> {
      categoryRepository.save(Category.builder().name("식비").build());
      categoryRepository.save(Category.builder().name("주거").build());
      categoryRepository.save(Category.builder().name("교통").build());
    });
  }
}
