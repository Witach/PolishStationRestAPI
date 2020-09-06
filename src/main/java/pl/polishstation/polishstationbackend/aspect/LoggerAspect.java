package pl.polishstation.polishstationbackend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggerAspect {
    @Before("within(@pl.polishstation.polishstationbackend.aspect.Loggable *)")
    void logControllerExecution(JoinPoint joinPoint) {
      log.info("Controller method {} has been executed", joinPoint.getSignature().toShortString());
    }
}
