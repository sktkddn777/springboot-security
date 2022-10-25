package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository 가 들고 있음
// JpaRepository를 상속했기에 @Repository 없어도 댐
public interface UserRepository extends JpaRepository<User, Integer> {

    // PrincipalDetailsService 에서 loadUserByUsername 함수에 쓰일 함수 정의
    // findBy 규칙
    // select * from user where username = username
    public User findByUsername(String username);
}
