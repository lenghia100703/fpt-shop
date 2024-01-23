package com.laptopshop.admin.controller;

import com.laptopshop.library.dto.AdminDto;
import com.laptopshop.library.dto.CustomerDto;
import com.laptopshop.library.model.Customer;
import com.laptopshop.library.service.AdminService;
import com.laptopshop.library.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final PasswordEncoder passwordEncoder;

    private final AdminService adminService;

    @GetMapping("/customers")
    public String customers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage Customer");
        List<Customer> customers = customerService.findALl();
        model.addAttribute("customers", customers);
        model.addAttribute("size", customers.size());
        model.addAttribute("customerNew", new Customer());
        return "customers";
    }

    @PostMapping("/save-customer")
    public String save(@ModelAttribute("customerNew") CustomerDto customer, Model model, RedirectAttributes redirectAttributes) {
        try {
            customerService.save(customer);
            model.addAttribute("customerNew", customer);
            redirectAttributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of customer, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            model.addAttribute("customerNew", customer);
            redirectAttributes.addFlashAttribute("error",
                    "Error server");
        }
        return "redirect:/customers";
    }

    @RequestMapping(value = "/findByCustomerId", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<Customer> findByCustomerId(Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/update-customer")
    public String update(CustomerDto customer, RedirectAttributes redirectAttributes) {
        try {
            customerService.update(customer);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of customer, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server or duplicate name of customer, please check again!");
        }
        return "redirect:/customers";
    }


    @RequestMapping(value = "/delete-customer", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of customer, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/customers";
    }

    @RequestMapping(value = "/enable-customer", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of customer, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/customers";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        AdminDto admin= adminService.getAdmin(username);
        model.addAttribute("admin", admin);
        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");
        return "customer-information";

    }

    @PostMapping("/update-profile")
    public String updateProfile(@Valid @ModelAttribute("admin") AdminDto adminDto,
                                BindingResult result,
                                RedirectAttributes attributes,
                                Model model,
                                Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        if (result.hasErrors()) {
            return "customer-information";
        }
        adminService.update(adminDto);
        AdminDto adminUpdate = adminService.getAdmin(principal.getName());
        attributes.addFlashAttribute("success", "Update successfully!");
        model.addAttribute("customer", adminUpdate);
        return "redirect:/profile";

    }

    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        return "change-password-admin";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("repeatNewPassword") String repeatPassword,
                             RedirectAttributes attributes,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            System.out.println(oldPassword);
            System.out.println(newPassword);
            System.out.println(repeatPassword);
            AdminDto adminDto = adminService.getAdmin(principal.getName());
            if (passwordEncoder.matches(oldPassword, adminDto.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, adminDto.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 5) {
                adminDto.setPassword(passwordEncoder.encode(newPassword));
                adminService.changePass(adminDto);
                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "Your password is wrong");
                return "change-password-admin";
            }
        }
    }
}
