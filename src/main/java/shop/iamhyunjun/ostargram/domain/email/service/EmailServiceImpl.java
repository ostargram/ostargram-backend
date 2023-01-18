package shop.iamhyunjun.ostargram.domain.email.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.iamhyunjun.ostargram.domain.email.entity.EmailValidation;
import shop.iamhyunjun.ostargram.domain.email.repository.EmailRepository;
import shop.iamhyunjun.ostargram.domain.user.entity.User;
import shop.iamhyunjun.ostargram.domain.user.repository.UserRepository;
import shop.iamhyunjun.ostargram.security.message.ResponseMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender emailSender;

    private static String hostAddress = "https://iamhyunjun.shop";
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public String emailLinkCheck(String emailLink, Long userId) {
        log.info("이메일 링크 확인 중");

        Optional<EmailValidation> emailValidationOptional = emailRepository.findByLink(emailLink);

        if (emailValidationOptional.isPresent()) {
            // user 이메일 인증여부 1로 변경
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.updateUserEmailCheck();
                log.info("이메일 완료");
            }


        } else { // 이메일 링크가 DB에 존재하지 않음.
            throw new IllegalArgumentException(ResponseMessage.EMAIL_FAIL_CHECK_LINK);
        }


        return null;
    }


    private MimeMessage createMessage(String to, String link, String username, Long userId) throws Exception {
        log.info("보내는 대상 : " + to);
        log.info("인증 번호 : " + link);


        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("이메일 인증 메일 입니다.");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> 안녕하세요 ostargram입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 인증 링크를 클릭해 주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다.<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<a href=\"" + hostAddress + "/users/email/" + link + "/" + userId + "\">";
        msgg += link + "</a><br/> ";

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("hyunjunhwang1994@gmail.com", "ostargram"));//보내는 사람

        return message;
    }

    public String createLink(String userId) throws NoSuchAlgorithmException {
        StringBuffer key = new StringBuffer();

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(userId.getBytes());


        return bytesToHex(messageDigest.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }


    @Override
    public String sendSimpleMessage(String to, String username, Long userId) throws Exception, SQLIntegrityConstraintViolationException {

        String link = createLink(username);
        MimeMessage message = createMessage(to, link, username, userId);

        try {//예외처리

            // DB에 이미 해당 유저의 인증링크가 존재한다면 Exception
            // 이메일 링크 저장부터 먼저.
            EmailValidation emailValidation = new EmailValidation(link);
            emailRepository.save(emailValidation);


            emailSender.send(message);

        } catch (MailException es) {
            throw new IllegalArgumentException("이메일 발송 오류입니다..");
        } catch (DataIntegrityViolationException es){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }


        return link;
    }


}