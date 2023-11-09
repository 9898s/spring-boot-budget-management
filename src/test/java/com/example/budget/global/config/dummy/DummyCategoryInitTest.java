package com.example.budget.global.config.dummy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.budget.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DummyCategoryInitTest extends DummyCategoryInit {

  @InjectMocks
  private DummyCategoryInit dummyCategoryInit;

  @Mock
  private CategoryRepository categoryRepository;

  @Test
  void 카테고리_실행_횟수_test() throws Exception {
    // when
    dummyCategoryInit.init(categoryRepository).run();

    // then
    verify(categoryRepository, times(3)).save(any());
  }
}