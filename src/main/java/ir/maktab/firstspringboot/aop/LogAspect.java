package ir.maktab.firstspringboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* ir.maktab.firstspringboot.service.*.*(..))")
    public void allServices() {
    }

    @Pointcut("execution(* ir.maktab.firstspringboot.util.*.*(..))")
    public void allUtils() {
    }

    @Before("allServices()")
    public void loggingAdviceForServiceBeforeAllMethods(JoinPoint joinPoint) {
        logger.info(
                joinPoint.getTarget().getClass().getSimpleName() + ": " +
                        joinPoint.getSignature().getName() + " called"
        );
    }

    @After("allServices()")
    public void loggingAdviceForServiceAfterAllMethods(JoinPoint joinPoint) {
        logger.debug(
                joinPoint.getTarget().getClass().getSimpleName() + ": " +
                        joinPoint.getSignature().getName() + " finished"
        );
    }

    @Before("allUtils()")
    public void loggingAdviceForUtilBeforeAllMethods(JoinPoint joinPoint) {
        logger.info(joinPoint.getTarget().getClass().getSimpleName() + ": " +
                joinPoint.getSignature().getName() + " called"
        );
    }

    @After("allUtils()")
    public void loggingAdviceForUtilAfterAllMethods(JoinPoint joinPoint) {
        logger.debug(joinPoint.getTarget().getClass().getSimpleName() + ": " +
                joinPoint.getSignature().getName() + " finished"
        );
    }

    @AfterThrowing(value = "allServices()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, RuntimeException ex) {
        logger.debug(joinPoint.getTarget().getClass().getSimpleName() + ": " +
                joinPoint.getSignature().getName() + " thrown exception: " + ex.getMessage()
        );
    }
}

