package com.wisestep.ibm.repository.email;

import com.wisestep.ibm.model.email.RedisEmailModel;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface EmailOTPRepository extends CrudRepository<RedisEmailModel,String> {
}
