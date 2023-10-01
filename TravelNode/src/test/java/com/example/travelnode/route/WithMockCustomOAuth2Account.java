package com.example.travelnode.route;

import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomOAuth2AccountSecurityContextFactory.class)
public @interface WithMockCustomOAuth2Account {

    String id() default "1234567";

    String email() default "stella@gmail.com";

    String nickname() default "Traveler";

    RoleType role() default RoleType.USER;

    ProviderType provider() default ProviderType.KAKAO;
}