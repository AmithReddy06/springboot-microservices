package com.bookstore.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.user_service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
