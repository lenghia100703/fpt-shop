package com.laptopshop.library.service;

import com.laptopshop.library.dto.AdminDto;
import com.laptopshop.library.model.Admin;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);

    AdminDto getAdmin(String username);

    Admin changePass(AdminDto adminDto);

    Admin update(AdminDto adminDto);
}
