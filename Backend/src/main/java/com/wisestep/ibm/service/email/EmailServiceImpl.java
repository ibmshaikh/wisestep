package com.wisestep.ibm.service.email;

import com.wisestep.ibm.model.email.EmailOTPConfig;
import com.wisestep.ibm.model.email.EmailOTPVerify;
import com.wisestep.ibm.model.email.RedisEmailModel;
import com.wisestep.ibm.model.user.User;
import com.wisestep.ibm.repository.email.EmailOTPRepository;
import com.wisestep.ibm.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private EmailOTPRepository emailOTPRepository;
    @Autowired
    private EmailOTPConfig emailOTPConfig;
    @Autowired
    private UserRepository userRepository;


    //send OTP method
    @Override
    public ResponseEntity<Object> sendOtp(String email) {
        try {
            //random OTP
            int OTP = generateRandomOTP();

            //random OTPUUID
            String id = UUID.randomUUID().toString();

            //saving email otp and otpUID to Redis db
            RedisEmailModel redis = new RedisEmailModel();
            redis.setEmail(email);
            redis.setUid(id);
            redis.setOTP(OTP);
            redis.setOtpExpiry(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(emailOTPConfig.getExpiry()));
            emailOTPRepository.save(redis);

            //Sending a mail
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setText("Your OTP is :" + OTP);
            emailSender.send(message);

            //Sending a response
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "Email sent successfully");
            response.put("id", id);
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error while sending email");
        }
    }

    //verify OTP method
    @Override
    public ResponseEntity<Object> verifyOTP(@RequestBody EmailOTPVerify emailOTPVerify) {
        Optional<RedisEmailModel> redisEmailCache = emailOTPRepository.findById(emailOTPVerify.getUid());

        //checking weather UID is present on not
        if (redisEmailCache.isPresent()) {
            //checking weather OTP expired or not
            if (redisEmailCache.get().getOtpExpiry() < System.currentTimeMillis()) {
                emailOTPRepository.deleteById(redisEmailCache.get().getUid());
                return ResponseEntity.status(498).body("OTP has expired");
            }

            int clientOTP = emailOTPVerify.getOtp();
            int actualOTP = redisEmailCache.get().getOTP();
            String userEmail = redisEmailCache.get().getEmail();

            if (clientOTP == actualOTP) {
                //deleting otp data after compare from redis
                emailOTPRepository.deleteById(redisEmailCache.get().getUid());
                Optional<User> user = userRepository.findById(userEmail);
                if (user.isPresent()) {
                    //checking user session is present
                    if (user.get().getSession() == null) {
                        String sessionID = UUID.randomUUID().toString();
                        user.get().setSession(sessionID);
                        return ResponseEntity.ok().body(userRepository.save(user.get()));
                    } else {
                        return ResponseEntity.status(HttpStatus.FOUND).body(user.get());
                    }
                } else {
                    return ResponseEntity.internalServerError().body("Something went wrong");
                }
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Provided OTP is incorrect");
        } else {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    public static int generateRandomOTP() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return Integer.parseInt(String.format("%06d", number));
    }

}
