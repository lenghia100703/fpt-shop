package com.laptopshop.admin.controller;

import com.laptopshop.admin.utils.RandomPasswordGenerator;
import com.laptopshop.admin.utils.SendMail;
import com.laptopshop.library.dto.AdminDto;
import com.laptopshop.library.dto.ForgotPasswordRequest;
import com.laptopshop.library.dto.ProductDto;
import com.laptopshop.library.model.Admin;
import com.laptopshop.library.repository.AdminRepository;
import com.laptopshop.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;

    private final BCryptPasswordEncoder passwordEncoder;

    private  final AdminRepository adminRepository;

    private final SendMail sendMail;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        model.addAttribute("forgotPasswordRequest", new ForgotPasswordRequest());
        return "forgot-password";
    }

    @PostMapping("/do-loss-pass")
    public String forgotPasswordNew(@ModelAttribute("forgotPasswordRequest") ForgotPasswordRequest forgotPasswordRequest) {
        try {
            System.out.println("hello" + forgotPasswordRequest.getEmail());
            Admin admin = adminRepository.findByUsername(forgotPasswordRequest.getEmail());
            RandomPasswordGenerator rpg = new RandomPasswordGenerator();
            String pass = rpg.generateRandomPassword();
            admin.setPassword(passwordEncoder.encode(pass));
            adminRepository.save(admin);
            String bodyEmail = "Mật khẩu mới cho tài khoản " + admin.getUsername() + " là: " + pass;
            sendMail.sendMailRender(admin.getUsername(), "PASSWORD RECOVER", bodyEmail);
        } catch (Exception e) {
            log.error("Error when send password to email: " + e.getMessage());
        }
        return "redirect:/login";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model) {

        try {

            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("admin not null");
                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("adminDto", adminDto);
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";

    }
}
