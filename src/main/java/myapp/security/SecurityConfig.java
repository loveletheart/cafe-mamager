package myapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 테스트용으로 CSRF 비활성화, 실제 서비스에서는 주의 필요
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()  // /register를 포함하여 접근 허용
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")  // 커스텀 로그인 페이지
                    .loginProcessingUrl("/login")  // 로그인 처리 URL
                    .usernameParameter("id")  // 기본 "username" 대신 "id" 사용
                    .passwordParameter("password")  // 패스워드 필드 명시
                    .successHandler(new CustomAuthenticationSuccessHandler()) // 커스텀 성공 핸들러 적용
                    .failureUrl("/login?error=true")  // 로그인 실패 시 이동
                    .permitAll()
                )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 설정
    }
}
