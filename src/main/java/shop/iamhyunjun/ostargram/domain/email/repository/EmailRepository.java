package shop.iamhyunjun.ostargram.domain.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.iamhyunjun.ostargram.domain.email.entity.EmailValidation;
import shop.iamhyunjun.ostargram.domain.user.entity.User;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailValidation, Long> {

    Optional<EmailValidation> findByLink(String link);
}
