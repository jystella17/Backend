package com.example.travelnode.oauth2.repository;

import com.example.travelnode.entity.Token;
import com.example.travelnode.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
