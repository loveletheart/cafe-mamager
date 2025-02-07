package myapp.controller;

import myapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class loginController {

    private final UserService userService;

    public loginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "로그아웃 되었습니다.");
        }
        return "/login";
    }

    /**
     * 회원가입 페이지를 보여줍니다.
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        return "/register";
    }

    /**
     * 회원가입 폼을 처리합니다.
     * 폼에서 전달된 id와 password를 사용해 회원가입을 수행하고,
     * 회원가입에 성공하면 로그인 페이지로 리디렉션하고, 실패하면 다시 회원가입 페이지를 표시합니다.
     */
    @PostMapping("/register")
    public String processRegistration(
            @RequestParam String id,
            @RequestParam String password,
            Model model) {

        boolean success = userService.registerUser(id, password);

        if (!success) {
            // 회원가입 실패 시 오류 메시지 모델에 추가하고 회원가입 페이지를 다시 보여줍니다.
            model.addAttribute("errorMessage", "이미 존재하는 ID입니다.");
            return "/register";
        }
        // 회원가입 성공 시 로그인 페이지로 리디렉션
        return "redirect:/login";
    }
}
