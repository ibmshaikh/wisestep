package com.wisestep.ibm.repository.user;

import com.wisestep.ibm.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findUserBySession(String session);
}
