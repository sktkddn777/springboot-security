package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login")을 해줬기에
// login 요청이 오면, 자동으로 UserDetailService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행
// 이후 반환되는 User 정보가 Authentication 에 담기고, 이 Authentication 은 시큐리티 session 에 담기게 되는 거다
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 함수에 String username 은 loginForm 폼에서 작성한 <input name="username" 이거랑 같아야 한다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null)
            return new PrincipalDetails(userEntity);
        return null;
    }
}
