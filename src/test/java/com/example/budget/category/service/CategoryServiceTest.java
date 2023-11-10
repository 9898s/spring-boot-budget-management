package com.example.budget.category.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.budget.category.dto.CategoryResponseDto.CategoryListResponseDto;
import com.example.budget.category.entity.Category;
import com.example.budget.category.repository.CategoryRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @InjectMocks
  private CategoryService categoryService;

  @Mock
  private CategoryRepository categoryRepository;

  @Test
  void 카테고리_목록_조회_test() throws Exception {
    // given
    Category 가 = Category.builder().id(1L).name("가").build();
    Category 나 = Category.builder().id(2L).name("나").build();
    Category 다 = Category.builder().id(3L).name("다").build();

    List<Category> categories = Arrays.asList(가, 나, 다);

    // stub 1
    when(categoryRepository.findAll()).thenReturn(categories);

    // when
    CategoryListResponseDto responseDto = categoryService.getCategories();

    // then
    assertEquals(3, responseDto.getCategories().size());

    // 가
    assertEquals(1, responseDto.getCategories().get(0).getId());
    assertEquals("가", responseDto.getCategories().get(0).getName());

    // 나
    assertEquals(2, responseDto.getCategories().get(1).getId());
    assertEquals("나", responseDto.getCategories().get(1).getName());

    // 다
    assertEquals(3, responseDto.getCategories().get(2).getId());
    assertEquals("다", responseDto.getCategories().get(2).getName());
  }
}