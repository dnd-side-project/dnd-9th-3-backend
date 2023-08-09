package com.dnd.gooding.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.gooding.domain.user.model.User;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
