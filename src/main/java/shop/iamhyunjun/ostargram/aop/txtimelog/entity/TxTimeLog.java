package shop.iamhyunjun.ostargram.aop.txtimelog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.security.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class TxTimeLog extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long transactionTime;
    private String methodName;

    public TxTimeLog(Long txTime, String methodName) {
        this.transactionTime = txTime;
        this.methodName = methodName;
    }
}
