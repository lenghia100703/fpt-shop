package com.laptopshop.admin.controller;

import com.laptopshop.library.model.Brand;
import com.laptopshop.library.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/brands")
    public String brands(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage Brand");
        List<Brand> brands = brandService.findALl();
        model.addAttribute("brands", brands);
        model.addAttribute("size", brands.size());
        model.addAttribute("brandNew", new Brand());
        return "brands";
    }

    @PostMapping("/save-brand")
    public String save(@ModelAttribute("brandNew") Brand brand, Model model, RedirectAttributes redirectAttributes) {
        try {
            brandService.save(brand);
            model.addAttribute("brandNew", brand);
            redirectAttributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of brand, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            model.addAttribute("brandNew", brand);
            redirectAttributes.addFlashAttribute("error",
                    "Error server");
        }
        return "redirect:/brands";
    }

    @RequestMapping(value = "/findByBrandId", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<Brand> findByBrandId(Long id) {
        return brandService.findById(id);
    }

    @GetMapping("/update-brand")
    public String update(Brand brand, RedirectAttributes redirectAttributes) {
        try {
            brandService.update(brand);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of brand, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server or duplicate name of brand, please check again!");
        }
        return "redirect:/brands";
    }


    @RequestMapping(value = "/delete-brand", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            brandService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of brand, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/brands";
    }

    @RequestMapping(value = "/enable-brand", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes redirectAttributes) {
        try {
            brandService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of brand, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/brands";
    }
}
