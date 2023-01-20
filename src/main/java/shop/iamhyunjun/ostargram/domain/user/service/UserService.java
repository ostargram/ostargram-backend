package shop.iamhyunjun.ostargram.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.iamhyunjun.ostargram.domain.email.repository.EmailRepository;
import shop.iamhyunjun.ostargram.domain.email.service.EmailServiceImpl;
import shop.iamhyunjun.ostargram.domain.user.dto.UserLoginResponseDto;
import shop.iamhyunjun.ostargram.domain.user.dto.UserSignupRequestDto;
import shop.iamhyunjun.ostargram.domain.user.dto.UserSignupResponseDto;
import shop.iamhyunjun.ostargram.domain.user.entity.User;
import shop.iamhyunjun.ostargram.domain.user.entity.UserRoleEnum;
import shop.iamhyunjun.ostargram.domain.user.repository.UserRepository;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsServiceImpl;



import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private final PasswordEncoder passwordEncoder;

//    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto) throws Exception {

        String username = userSignupRequestDto.getUsername();
        String password = passwordEncoder.encode(userSignupRequestDto.getPassword());
        String email = userSignupRequestDto.getEmail();
        String gender = userSignupRequestDto.getGender();

        // 회원 중복 확인
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 이메일 중복 확인
        Optional<User> userEmail = userRepository.findByEmail(email);
        if (userEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        User user = new User(username, password, email, gender);
        userRepository.save(user);

        // 이메일 발송
        emailService.sendSimpleMessage(user.getEmail(), user.getUsername(), user.getId());


        return new UserSignupResponseDto(201, "회원 가입 성공");
    }


}
