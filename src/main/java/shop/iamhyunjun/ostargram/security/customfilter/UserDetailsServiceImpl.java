package shop.iamhyunjun.ostargram.security.customfilter;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.iamhyunjun.ostargram.domain.user.entity.User;
import shop.iamhyunjun.ostargram.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }


        User user = userOptional.get();
        return new UserDetailsImpl(user, user.getUsername(), user.getPassword());
    }

}