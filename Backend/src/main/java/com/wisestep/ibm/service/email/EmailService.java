package com.wisestep.ibm.service.email;

import com.wisestep.ibm.model.email.EmailOTPVerify;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailService {

    ResponseEntity<Object> sendOtp(String email);
    ResponseEntity<Object> verifyOTP(@RequestBody EmailOTPVerify emailOTPVerify);

}
