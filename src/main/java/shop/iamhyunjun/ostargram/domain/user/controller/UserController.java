package shop.iamhyunjun.ostargram.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shop.iamhyunjun.ostargram.domain.user.dto.SignupRequestDto;
import shop.iamhyunjun.ostargram.domain.user.entity.Test;
import shop.iamhyunjun.ostargram.domain.user.entity.TestRepository;
import shop.iamhyunjun.ostargram.domain.user.entity.User;
import shop.iamhyunjun.ostargram.domain.user.entity.UserRoleEnum;
import shop.iamhyunjun.ostargram.domain.user.repository.UserRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final TestRepository testRepository;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {

        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());


        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        System.out.println("signup Controller");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        return "redirect:/login-page";
    }

    @PostMapping("/login")
    public String login(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("*********************************************************");
        System.out.println("UserController.login");
        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
        System.out.println("*********************************************************");

        return "redirect:/users/login-page";
    }

    @GetMapping("/test")
    public void test() {

        Test test = new Test();
        testRepository.save(test);
        System.out.println(test.getCreatedAt());
        System.out.println(test.getCreatedBy());
        System.out.println(test.getModifiedAt());

    }

}