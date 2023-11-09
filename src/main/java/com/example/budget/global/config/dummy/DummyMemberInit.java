package com.example.budget.global.config.dummy;

import com.example.budget.member.entity.Member;
import com.example.budget.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyMemberInit {

  @Bean
  CommandLineRunner init(MemberRepository memberRepository) {
    return (args -> {
      memberRepository.save(Member.builder().account("kim").build());
      memberRepository.save(Member.builder().account("su").build());
      memberRepository.save(Member.builder().account("hwan").build());
    });
  }
}
