package com.viendong.BUOI8;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Kiểm tra quyền của người dùng và chuyển hướng đến trang phù hợp
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            // Điều hướng tới trang danh sách sản phẩm nếu là ADMIN
            response.sendRedirect("/admin?loginSuccess=true");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            // Điều hướng tới trang danh sách người dùng nếu là USER
            response.sendRedirect("/users?loginSuccess=true");
        } else {
            // Mặc định điều hướng tới trang chính
            response.sendRedirect("/");
        }
    }
}