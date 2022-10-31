package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어줌 (Session 정보는 Security ContextHolder 라는 키값에다가 세션 정보를 저장)
// 세션에 정보에 들어갈 오브젝트가 정해져 있음 (오브젝트 타입 => Authentication 타입 객체)
// Authentication 안에 User 정보가 있어야 됨 (유저 오브젝트 타입 => UserDetails 타입 객체)
// 결국 Session => Authentication => UserDetails 객체를 꺼내면 유저 정보를 얻을 수 있음

public class PrincipalDetails implements UserDetails {

    private User user; // 유저 정보

    public PrincipalDetails(User user) {
        this.user = user;
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
}
