package shop.iamhyunjun.ostargram.domain.user.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.security.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Test extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
