package com.agarwal.blog.blogapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestHeader = request.getHeader("Authorization");

        if (requestHeader.startsWith("Bearer")) {
            String token = requestHeader.substring(7);
            String username = this.jwtTokenHelper.getUsernameFromToken(token);


        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
        return false;
    }
}
