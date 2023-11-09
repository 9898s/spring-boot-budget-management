package com.example.budget.category.dto;

import com.example.budget.category.entity.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

public class CategoryResponseDto {

  @Setter
  @Getter
  public static class CategoryListResponseDto {

    private List<CategoryDto> categories = new ArrayList<>();

    public CategoryListResponseDto(List<Category> categories) {
      this.categories = categories.stream()
          .map(CategoryDto::new)
          .collect(Collectors.toList());
    }

    @Setter
    @Getter
    public class CategoryDto {

      private final Long id;
      private final String name;

      public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
      }
    }
  }
}
