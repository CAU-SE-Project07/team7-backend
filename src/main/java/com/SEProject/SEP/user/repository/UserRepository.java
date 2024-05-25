package com.SEProject.SEP.user.repository;

import com.SEProject.SEP.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {// 기본키 타입이 long
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserNameAndPassword(String userName, String userPassword);
}
