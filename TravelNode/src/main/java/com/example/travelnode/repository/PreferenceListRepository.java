package com.example.travelnode.repository;

import com.example.travelnode.entity.PreferenceList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceListRepository extends JpaRepository<PreferenceList, Long> {
}