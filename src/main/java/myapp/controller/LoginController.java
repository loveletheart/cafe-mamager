package myapp.controller;

import myapp.entity.UserData;
import myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    
    // QR 코드 스캔 후 로그인 처리
    @GetMapping("/qr-login/{id}")
    public String loginWithQRCode(@PathVariable String id, Model model) {
        Optional<UserData> user = userService.getUserByQRCode(id);
        if (user.isPresent()) {
            model.addAttribute("message", "로그인 성공! ID: " + id);
            return "login_success"; // 로그인 성공 화면
        } else {
            model.addAttribute("message", "QR 코드가 유효하지 않습니다.");
            return "qr_login";
        }
    }
    
    @PostMapping("/login/qr")
    public String loginWithQR(@RequestParam String qrCode, Model model) {
        Optional<UserData> user = userService.getUserByQRCode(qrCode);
        if (user.isPresent()) {
            // QR 코드로 찾은 사용자 정보를 세션에 저장하여 로그인 처리
            UserData loggedInUser = user.get();
            model.addAttribute("user", loggedInUser);
            
            // 바로 메뉴 페이지로 이동
            return "redirect:/menu";
        }

        //로그인 실패 시 QR 로그인 페이지로 다시 이동
        model.addAttribute("loginError", "QR 코드가 유효하지 않습니다.");
        return "QRlogin";
    }
}
