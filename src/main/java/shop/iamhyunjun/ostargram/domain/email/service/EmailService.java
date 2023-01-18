package shop.iamhyunjun.ostargram.domain.email.service;

public interface EmailService {
    String sendSimpleMessage(String to, String username, Long userId)throws Exception;

    String emailLinkCheck(String emailLink, Long userId);
}