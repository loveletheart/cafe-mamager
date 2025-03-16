package myapp.controller;

import myapp.entity.UserData;
import myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController {
	
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "로그아웃 되었습니다.");
        }
        return "login";
    }
    
    /**
     * 회원가입 페이지를 보여줍니다.
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        return "register";
    }
    
    /**
     * 회원가입 폼을 처리합니다.
     * 폼에서 전달된 id와 password를 사용해 회원가입을 수행하고,
     * 회원가입에 성공하면 로그인 페이지로 리디렉션하고, 실패하면 다시 회원가입 페이지를 표시합니다.
     */
    @PostMapping("/register")
    public String processRegistration(@RequestParam String id,
                                      @RequestParam String password,
                                      @RequestParam String username,
                                      @RequestParam String role,
                                      Model model) {
        System.out.println("회원가입 요청 받음: " + id);
        boolean success = userService.registerUser(id, password, username, role);
        
        if (!success) {
            model.addAttribute("errorMessage", "이미 존재하는 ID 또는 Username입니다.");
            return "register"; // 실패 시 회원가입 페이지 다시 표시
        }
        return "redirect:/menu"; // 성공 시 로그인 페이지로 이동
    }
    
    /**
     * qr로그인페이지를 보여줍니다
     */
    @GetMapping("/QRlogin")
    public String showQRLoginPage(Model model) {
        model.addAttribute("ids", new int[]{1, 2, 3, 4, 5}); // 샘플 ID 리스트
        return "QRlogin";
    }
    
    /**
     * qr인식시 qr의 값을 전달하여 로그인함
     */
    @PostMapping("/qr")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> qrLogin(@RequestParam String qrCode, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Optional<UserData> userOptional = userService.getUserByQRCode(qrCode);
        if (userOptional.isPresent()) {
            UserData user = userOptional.get();
            
            // Spring Security 인증 객체 생성
            UsernamePasswordAuthenticationToken auth = 
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth); // 인증 설정
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 세션에 인증 정보 저장

            response.put("success", true);
            response.put("redirectUrl", "/menu"); 
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "QR 코드가 유효하지 않습니다.");
            return ResponseEntity.status(401).body(response);
        }
    }
}
