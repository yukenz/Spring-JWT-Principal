package com.awan.securityjwt.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserDetailServiceAspect {

    @Around("execution(* org.springframework.security.core.userdetails.UserDetailsService.*(..)) throws UsernameNotFoundException")
    public Object userDetailsService(ProceedingJoinPoint pjp) {

        /* Get Full Class Name */
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Object returner = null;

        try {
            log.info("Start Execute {}.{}()", className, methodName);
            returner = pjp.proceed(pjp.getArgs());
            return returner;
        } catch (Throwable ex) {
            log.info("Failed Execute {}.{}() - {}", className, methodName, ex.getMessage());
            throw (UsernameNotFoundException) ex;
        } finally {
            log.info("End Execute {}.{}()",className, methodName);
        }
    }

}
