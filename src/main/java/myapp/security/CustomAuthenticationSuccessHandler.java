package myapp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 인증 성공 시, 인증된 사용자 이름 가져오기
        String username = authentication.getName();
        
        // 사용자 아이디가 "master"라면 ods/ODS 페이지로 이동
        if ("master".equals(username)) {
            response.sendRedirect(request.getContextPath() + "/ods/ODS");
        } else {
            // 그 외의 경우 기본 성공 URL로 이동 (예: /menu)
            response.sendRedirect(request.getContextPath() + "/menu");
        }
    }
}
