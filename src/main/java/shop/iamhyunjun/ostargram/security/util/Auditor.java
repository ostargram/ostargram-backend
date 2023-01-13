package shop.iamhyunjun.ostargram.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;

import java.util.Optional;

@Slf4j
public class Auditor implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 기본 값이 anonymousUser String
        if (principal instanceof UserDetailsImpl) {
            Long id = ((UserDetailsImpl) principal).getUser().getId();

            return Optional.of(id);
        }

        return Optional.empty();
    }
}
