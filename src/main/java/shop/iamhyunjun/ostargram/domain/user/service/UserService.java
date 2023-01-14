package shop.iamhyunjun.ostargram.domain.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.iamhyunjun.ostargram.domain.user.dto.UserSignupRequestDto;
import shop.iamhyunjun.ostargram.domain.user.dto.UserSignupResponseDto;
import shop.iamhyunjun.ostargram.domain.user.entity.User;
import shop.iamhyunjun.ostargram.domain.user.entity.UserRoleEnum;
import shop.iamhyunjun.ostargram.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto) {


        String username = userSignupRequestDto.getUsername();
        String password = passwordEncoder.encode(userSignupRequestDto.getPassword());


        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
//        if (signupRequestDto.isAdmin()) {
//            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }

        User user = new User(username, password, role);
        userRepository.save(user);

        return new UserSignupResponseDto(200, "회원 가입 성공");
    }

}
