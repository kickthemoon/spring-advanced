package org.example.expert.config;


import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class AdminApiLoggingAspect {

    @Pointcut("execution(* org.example.expert.domain.comment.controller..*.*(..))")
    private void commentAdminController() {}
    @Pointcut("execution(* org.example.expert.domain.user.controller..*.*(..))")
    private void userAdminController() {}

    @Around("commentAdminController() || userAdminController()")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws  Throwable {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        String username = (String) request.getSession().getAttribute("username"); // 세션에 저장된 사용자명
        String ip = request.getRemoteAddr();
        LocalDateTime now = LocalDateTime.now();
        String url = request.getRequestURL().toString();
        String query = request.getQueryString();
        String fullUrl = (query != null) ? url + "?" + query : url;

        log.info("관리자 접속 - 사용자명: {}, IP: {}, 시간: {}, URL: {}", username, ip, now, fullUrl);

        return joinPoint.proceed();
    }
}
