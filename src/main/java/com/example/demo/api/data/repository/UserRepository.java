package com.example.demo.api.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.api.data.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
