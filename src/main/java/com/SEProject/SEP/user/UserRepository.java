package com.SEProject.SEP.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser,Long> {// 기본키 타입이 long
    Optional<SiteUser> findByUserid(String userid);
}
