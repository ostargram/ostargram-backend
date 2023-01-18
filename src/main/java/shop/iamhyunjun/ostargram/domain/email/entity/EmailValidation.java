package shop.iamhyunjun.ostargram.domain.email.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.security.entity.TimeStamped;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class EmailValidation extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String link;


    public EmailValidation(String link) {
        this.link = link;
    }
}
