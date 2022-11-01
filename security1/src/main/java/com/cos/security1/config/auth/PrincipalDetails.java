package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어줌 (Session 정보는 Security ContextHolder 라는 키값에다가 세션 정보를 저장)
// 세션에 정보에 들어갈 오브젝트가 정해져 있음 (오브젝트 타입 => Authentication 타입 객체)
// Authentication 안에 User 정보가 있어야 됨 (유저 오브젝트 타입 => UserDetails 타입 객체)
// 결국 Session => Authentication => UserDetails 객체를 꺼내면 유저 정보를 얻을 수 있음

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // 유저 정보
    private Map<String, Object> attributes;

    // 일반 로그인 할 때
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // Oauth 로그인 할 때
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료 안되었니??
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 니 계정이 안잠겼니??
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 니 계정이 만료되지 않았니??
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 니 계정이 활성화 됐니??
    @Override
    public boolean isEnabled() {

        // (예시) 우리 사이트에서 1년동안 로그인 안하면 휴면 계정으로..!!

        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 아래 함수는 안쓰인다고 함
    @Override
    public String getName() {
        return null;
    }
}
