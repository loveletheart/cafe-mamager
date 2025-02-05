package myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class loginController {
	
	@GetMapping("/login")
	public String getlogin() {
		return "login";
	}
}
