package shop.iamhyunjun.ostargram.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.validation.annotation.Validated;
import shop.iamhyunjun.ostargram.domain.email.entity.EmailValidation;
import shop.iamhyunjun.ostargram.security.entity.TimeStamped;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User extends TimeStamped implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int emailCheck;



    public User(String username, String password, String email, String gender) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
    }

    public void updateUserEmailCheck() {
        this.emailCheck = 1;
    }
}