package com.laptopshop.library.service.impl;

import com.laptopshop.library.dto.AdminDto;
import com.laptopshop.library.model.Admin;
import com.laptopshop.library.repository.AdminRepository;
import com.laptopshop.library.repository.RoleRepository;
import com.laptopshop.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;


    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public AdminDto getAdmin(String username) {
        AdminDto adminDto = new AdminDto();
        Admin admin = adminRepository.findByUsername(username);
        adminDto.setFirstName(admin.getFirstName());
        adminDto.setLastName(admin.getLastName());
        adminDto.setUsername(admin.getUsername());
        adminDto.setPassword(admin.getPassword());
        return adminDto;
    }

    @Override
    public Admin changePass(AdminDto adminDto) {
        Admin admin = adminRepository.findByUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        return adminRepository.save(admin);
    }

    @Override
    public Admin update(AdminDto adminDto) {
        Admin admin = adminRepository.findByUsername(adminDto.getUsername());
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        return adminRepository.save(admin);
    }
}
