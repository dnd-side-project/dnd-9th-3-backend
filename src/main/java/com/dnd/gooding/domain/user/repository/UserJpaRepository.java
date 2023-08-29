package com.dnd.gooding.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.gooding.domain.user.model.User;
import com.dnd.gooding.domain.user.service.port.UserRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {
}
