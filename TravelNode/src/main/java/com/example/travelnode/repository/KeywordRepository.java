package com.example.travelnode.repository;

import com.example.travelnode.entity.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeywordRepository extends JpaRepository<Keywords, Long> {

    @Query("SELECT k.keyword FROM Keywords k WHERE k.keyId = ?1")
    Keywords findKeywordsByKeyId(Long keyId);
}
