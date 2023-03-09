package com.example.travelnode.repository;

import com.example.travelnode.entity.KeywordList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<KeywordList, Long> {
}
