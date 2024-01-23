package com.laptopshop.customer.utils;

import com.laptopshop.library.model.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class OtpUtil {

    public String generateOTP() {
        Random random = new Random();
        int randomInt = random.nextInt(999999);
        return String.format("%06d", randomInt);
    }

    public boolean validateOTP(Customer customer, String otp, LocalDateTime generatedTime) {
        LocalDateTime now = LocalDateTime.now();
        return customer.getOtp().equals(otp) && generatedTime.plusMinutes(5).isAfter(now);
    }
}
