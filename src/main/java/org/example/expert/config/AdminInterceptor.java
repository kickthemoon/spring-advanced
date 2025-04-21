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

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
