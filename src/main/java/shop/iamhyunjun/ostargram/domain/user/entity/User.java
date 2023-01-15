package shop.iamhyunjun.ostargram.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.security.entity.TimeStamped;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String checkPassword;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;

    public User(String username, String password, String checkPassword, String email, String gender) {

        this.username = username;
        this.password = password;
        this.checkPassword = checkPassword;
        this.email = email;
        this.gender = gender;
    }
}