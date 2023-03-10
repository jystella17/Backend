package com.example.travelnode.service;

import com.example.travelnode.entity.RoleType;
import com.example.travelnode.entity.User;
import com.example.travelnode.oauth2.entity.UserPrincipal;
import com.example.travelnode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User findUser(Long uid){
        return userRepository.getReferenceById(uid);
    }

    @Transactional
    public User changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, RoleType roleType) {
        User user = userRepository.findByUniqueId(userPrincipal.getUniqueId());
        user.changeRole(roleType);

        return user;
    }
}
