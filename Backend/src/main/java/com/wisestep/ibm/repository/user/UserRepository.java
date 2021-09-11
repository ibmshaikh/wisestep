package com.wisestep.ibm.repository.user;

import com.wisestep.ibm.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findUserBySession(String session);
}
