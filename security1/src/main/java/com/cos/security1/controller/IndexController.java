package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 일반 로그인을 하게 되면 authentication 객체에 UserDetails 타입이 들어오고
    // oauth 로 로그인을 하게 되면 OAuth2User 타입이 들어오게 된다
    @GetMapping("/test/login")
    @ResponseBody
    public String testLogin(Authentication authentication, // DI (의존성 주입)
                            @AuthenticationPrincipal PrincipalDetails userDetails) { // 어노테이션을 통해서 세션 정보에 접근 가능
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        System.out.println(principalDetails.getUser()); // 방법 1
        System.out.println(userDetails.getUser()); // 방법 2
        return "test/login";
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String testOauthLogin(Authentication authentication,
                                 @AuthenticationPrincipal OAuth2User oAuth) {// DI (의존성 주입)

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println(oAuth2User.getAttributes()); // oauth 방법 1
        System.out.println(oAuth.getAttributes()); // oauth 방법 2

        return "test/oauth/login";
    }

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    // OAuth 로그인을 해도 PrincipalDetails
    // 일반 로그인을 해도 PrincipalDetails
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principal: " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user); // 비밀번호 그대로 노출됨, 패스워드 암호화 필요

        return "redirect:/login";
    }

    @GetMapping("/joinProc")
    @ResponseBody
    public String joinProc() {
        return "sign up done";
    }

    @GetMapping("/info")
    @Secured("ROLE_ADMIN") // ADMIN만 들어갈 수 있음
    @ResponseBody
    public String info() {
        return "personal info";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 두가지 역할을 동시에 지정할 수 있음
    @GetMapping("/data")
    @ResponseBody
    public String data() {
        return "data";
    }
}
