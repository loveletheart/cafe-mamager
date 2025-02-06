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
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return "login";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        boolean success = userService.registerUser(username, password);
        
        if (!success) {
            model.addAttribute("error", "이미 존재하는 아이디입니다.");
            return "login/register";
        }

        return "redirect:/login";
    }
}
