package exercise.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Around("@annotation(exercise.aspects.Time) && execution(public * *(..))")
    public Object calculoTiempo(ProceedingJoinPoint joinPoint) throws Throwable {

        long t1=System.currentTimeMillis();
        Object resultado=joinPoint.proceed();
        long t2=System.currentTimeMillis();

        if( t2-t1>2000) {
            log.info("Slow method: "+ joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName() +" : "+ (t2-t1)+"milisegundos");
        }

        return resultado;

    }

}

