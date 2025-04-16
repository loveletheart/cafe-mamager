package myapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.requiresChannel(channel -> channel
                .anyRequest().requiresSecure() // 모든 요청을 HTTPS로 강제 리디렉션
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/QRlogin","/QRredirect").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")  // 로그인 요청을 처리하는 URL
                .successHandler(customAuthenticationSuccessHandler) // 성공 핸들러 등록
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            );
        http.
        	sessionManagement(session -> session.
        			sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        		);

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 설정
    }
}
