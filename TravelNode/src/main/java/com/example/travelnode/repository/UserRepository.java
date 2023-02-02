package com.example.travelnode.repository;

import com.example.travelnode.dto.UserInfoDto;
import com.example.travelnode.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.uid, u.email, u.userId, u.nickname, u.roleType, u.providerType, u.gender, u.age, " +
            "u.profileImgUrl, u.profileImgName, u.createdAt, u.modifiedAt FROM User u")
    List<UserInfoDto> findAllByUid(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.nickname = ?1")
    User findByNickname(String nickname);
}
