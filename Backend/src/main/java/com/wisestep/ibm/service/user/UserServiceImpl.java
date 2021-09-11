package com.wisestep.ibm.service.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.wisestep.ibm.controller.email.EmailOTPController;
import com.wisestep.ibm.model.user.User;
import com.wisestep.ibm.repository.user.UserRepository;
import com.wisestep.ibm.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<Object> createUser(@Validated @RequestBody User user) {
        if (userRepository.findById(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email: " + user.getEmail() + " Already Exists");

        } else {
            try {
                String bcryptHashString = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
                user.setPassword(bcryptHashString);
                userRepository.save(user);
                //Send OTP
                return emailService.sendOtp(user.getEmail());
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Something went wrong");
            }
        }
    }

    @Override
    public ResponseEntity<Object> login(@RequestBody User userLogin) {
        Optional<User> user = userRepository.findById(userLogin.getEmail());
        if (user.isPresent()) {
            BCrypt.Result result = BCrypt.verifyer().verify(userLogin.getPassword().toCharArray(), user.get().getPassword());
            if (result.verified) {
                //SEND OTP
                return emailService.sendOtp(userLogin.getEmail());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email or password incorrect");
            }
        } else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email or password incorrect");
    }

    @Override
    public ResponseEntity<Object> getUser(@PathVariable String sessionID) {

        User session = userRepository.findUserBySession(sessionID);
        if (session != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> invalidateSession(@PathVariable String email) {

        Optional<User> session = userRepository.findById(email);
        if (session.isPresent()) {
            String newSession = UUID.randomUUID().toString();
            session.get().setSession(newSession);
            return ResponseEntity.ok().body(userRepository.save(session.get()));

        } else
            return ResponseEntity.internalServerError().body("something went wrong");
    }

    @Override
    public ResponseEntity<Object> loggedOut(@PathVariable String email) {

        Optional<User> session = userRepository.findById(email);
        if (session.isPresent()) {
            session.get().setSession(null);
            userRepository.save(session.get());
            return ResponseEntity.ok().build();

        } else
            return ResponseEntity.internalServerError().body("something went wrong");
    }
}
