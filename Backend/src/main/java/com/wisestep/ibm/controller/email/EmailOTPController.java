package com.wisestep.ibm.controller.email;


import com.wisestep.ibm.model.email.EmailOTPConfig;
import com.wisestep.ibm.model.email.EmailOTPVerify;
import com.wisestep.ibm.model.email.RedisEmailModel;
import com.wisestep.ibm.repository.email.EmailOTPRepository;
import com.wisestep.ibm.model.user.User;
import com.wisestep.ibm.repository.user.UserRepository;
import com.wisestep.ibm.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequestMapping("/api/v1/notification/mail")
public class EmailOTPController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/otp/verify")
    public ResponseEntity<Object> verifyOTP(@RequestBody EmailOTPVerify emailOTPVerify) {
        return emailService.verifyOTP(emailOTPVerify);
    }
}
