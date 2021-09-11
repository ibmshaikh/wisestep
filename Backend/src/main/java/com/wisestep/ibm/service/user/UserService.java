package com.wisestep.ibm.service.user;

import com.wisestep.ibm.model.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    public abstract ResponseEntity<Object> createUser(User user);
    public abstract ResponseEntity<Object> login(User userLogin);
    public abstract ResponseEntity<Object> getUser(String sessionID);
    public abstract ResponseEntity<Object> invalidateSession(String email);
    public abstract ResponseEntity<Object> loggedOut(String email);
}
