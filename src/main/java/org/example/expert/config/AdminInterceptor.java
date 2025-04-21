package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.auth.exception.AccessDeniedException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.example.expert.domain.user.enums.UserRole;

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        Object role = request.getSession().getAttribute("userRole");

        if(!(role instanceof UserRole && role == UserRole.ADMIN)) {
            throw new AccessDeniedException("관리자만 접근할 수 있습니다.");
        }

        String username = (String) request.getSession().getAttribute("username"); // 세션에 저장된 사용자명
        String ip = request.getRemoteAddr();
        LocalDateTime now = LocalDateTime.now();
        String url = request.getRequestURL().toString();
        String query = request.getQueryString();
        String fullUrl = (query != null) ? url + "?" + query : url;

        log.info("관리자 접속 - 사용자명: {}, IP: {}, 시간: {}, URL: {}", username, ip, now, fullUrl);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
