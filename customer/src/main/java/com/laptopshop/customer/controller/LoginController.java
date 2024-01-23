package com.laptopshop.customer.controller;

import com.laptopshop.customer.utils.OtpUtil;
import com.laptopshop.customer.utils.SendMail;
import com.laptopshop.library.dto.CustomerDto;
import com.laptopshop.library.model.City;
import com.laptopshop.library.model.Customer;
import com.laptopshop.library.repository.CustomerRepository;
import com.laptopshop.library.service.CustomerService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OtpUtil otpUtil;
    private final SendMail sendMail;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        model.addAttribute("page", "Home");
        return "login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("page", "Register");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @GetMapping("/confirm-otp")
    public String confirmOtpPage(@RequestParam String username, @RequestParam String otp, Model model) {
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            model.addAttribute("error", "Invalid username!");
            return "login";
        }
        model.addAttribute("username", username);
        model.addAttribute("otp", otp);
        return "confirm-otp";
    }

    @PostMapping("/do-login")
    public String doLogin(@RequestParam String username, @RequestParam String password, Model model) {
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            model.addAttribute("error", "Invalid username!");
            return "login";
        }
        if (!customer.isActivated()) {
            model.addAttribute("error", "This account hasn't been verified yet.");
            return "login";
        }
        if (passwordEncoder.matches(password, customer.getPassword())) {
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Password is incorrect!");
            return "login";
        }
    }

    @PostMapping("/do-register")
    public String registerCustomer(@RequestParam String firstName,
                                      @RequestParam String lastName,
                                        @RequestParam String username,
                                          @RequestParam String phoneNumber,
                                            @RequestParam String address,
//                                              @RequestParam City city,
//                                                @RequestParam String country,
                                                      @RequestParam String password,
                                                        @RequestParam String confirmPassword,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (customerService.findByUsername(username) != null) {
                redirectAttributes.addFlashAttribute("error", "Email has been register!");
                return "register";
            }

            CustomerDto customerDto = new CustomerDto();
            customerDto.setFirstName(firstName);
            customerDto.setLastName(lastName);
            customerDto.setUsername(username);
            customerDto.setPassword(password);
            customerDto.setConfirmPassword(confirmPassword);

            customerDto.setPhoneNumber(phoneNumber);
            customerDto.setAddress(address);
//            customerDto.setCity(city);
//            customerDto.setCountry(country);

            String otp = otpUtil.generateOTP();
            if (password.equals(confirmPassword)) {
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));

                sendMail.sendOtpMail(username, otp);
                customerDto.setOtp(otp);
                customerDto.setOtpGeneratedTime(LocalDateTime.now());

                customerService.save(customerDto);
//                model.addAttribute("success", "Register successfully!");

                redirectAttributes.addFlashAttribute("info", "A mail with the new password has been sent to your email.");
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("error", "Confirm password is incorrect!");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Server is error, try again later!");
        }
        return null;
    }

    @PostMapping("/do-confirm-otp")
    public String confirmOtp(@RequestParam String username,
                             @RequestParam String otp,
                             Model model) {
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            model.addAttribute("error", "Invalid username!");
            return "login";
        }
        if (otpUtil.validateOTP(customer, otp, customer.getOtpGeneratedTime())) {
            customer.setActivated(true);
            customerRepository.save(customer);
            model.addAttribute("success", "OTP verified successfully !");
            return "login";
        } else if (!customer.getOtp().equals(otp)) {
            model.addAttribute("error", "OTP is incorrect!");
            return "confirm-otp";
        } else {
            model.addAttribute("error", "OTP is expired!");
            return "confirm-otp";
        }
    }

    @PostMapping("/do-resend-otp")
    public String resendOtp(@RequestParam String username,
                            Model model) throws MessagingException {
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            model.addAttribute("error", "Invalid username!");
            return "login";
        }
        String otp = otpUtil.generateOTP();
        sendMail.sendOtpMail(username, otp);
        customer.setOtp(otp);
        customer.setOtpGeneratedTime(LocalDateTime.now());
        customerRepository.save(customer);
        model.addAttribute("success", "OTP resent successfully !");
        return "confirm-otp";
    }
}
