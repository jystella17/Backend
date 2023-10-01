package com.example.travelnode.repository;

import com.example.travelnode.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferRepository extends JpaRepository<UserPreference, Long> {
}
