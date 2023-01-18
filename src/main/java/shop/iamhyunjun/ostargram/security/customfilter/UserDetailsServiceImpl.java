package shop.iamhyunjun.ostargram.security.customfilter;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.iamhyunjun.ostargram.domain.user.entity.User;
import shop.iamhyunjun.ostargram.domain.user.repository.UserRepository;
import shop.iamhyunjun.ostargram.security.message.ResponseMessage;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(ResponseMessage.LOGIN_FAIL_ID_OR_PASSWORD);
        }
        User user = userOptional.get();
        return new UserDetailsImpl(user, user.getUsername(), user.getPassword());
    }

    public int userEmailCheck(String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int emailCheck = user.getEmailCheck();

            if (emailCheck == 1) {
                return 1;
            }
        }
        throw new IllegalArgumentException(ResponseMessage.LOGIN_FAIL_CHECK_EMAIL);
    }

}