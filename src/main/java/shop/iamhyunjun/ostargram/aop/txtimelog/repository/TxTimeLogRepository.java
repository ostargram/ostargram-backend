package shop.iamhyunjun.ostargram.aop.txtimelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.iamhyunjun.ostargram.aop.txtimelog.entity.TxTimeLog;

public interface TxTimeLogRepository extends JpaRepository<TxTimeLog, Long> {
}
