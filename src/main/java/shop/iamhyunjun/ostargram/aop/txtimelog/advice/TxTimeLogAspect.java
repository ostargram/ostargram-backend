package shop.iamhyunjun.ostargram.aop.txtimelog.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import shop.iamhyunjun.ostargram.aop.txtimelog.entity.TxTimeLog;
import shop.iamhyunjun.ostargram.aop.txtimelog.repository.TxTimeLogRepository;

/**
 * DB 접속 시간이 3초 이상인 경우 해당 기록을 DB에 저장합니다.
 * 1. 병목지점이 어디인지 판단할 수 있음
 */

@Slf4j
@Aspect
@Component
@Order(2)
@RequiredArgsConstructor
public class TxTimeLogAspect {

    private final TxTimeLogRepository timeLogRepository;

    private static Integer declaringTypeNameStartIndex = 33;

    // 도메인 내부 모든 컨트롤러에 적용
    @Around("execution(* shop.iamhyunjun.ostargram.domain..*Controller.*(..))")
    public Object doTxTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long txBefore = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long txAfter = System.currentTimeMillis();

        Long txMillis = txAfter - txBefore;

        if (txMillis > 3000l) {
            String txSource = makeTxSource(joinPoint);
            log.warn("[TRANSACTION WARNING: {}ms]", txMillis);
            timeLogRepository.save(new TxTimeLog(txMillis, txSource));
        }

        return result;
    }

    private String makeTxSource(ProceedingJoinPoint joinPoint) {
        // 메소드 명
        String methodName = joinPoint.getSignature().getName();

        // 클래스 명
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        declaringTypeName = declaringTypeName.substring(declaringTypeNameStartIndex);

        String txSource = declaringTypeName + "." + methodName;
        return txSource;
    }
}

