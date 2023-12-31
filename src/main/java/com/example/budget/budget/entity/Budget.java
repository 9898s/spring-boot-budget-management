package com.example.budget.budget.entity;

import com.example.budget.category.entity.Category;
import com.example.budget.member.entity.Member;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@DynamicUpdate
@Entity
public class Budget {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category")
  private Category category;

  public void update(Long amount, Category category) {
    if (amount != null) {
      this.amount = amount;
    }

    if (category != null) {
      this.category = category;
    }
  }
}
