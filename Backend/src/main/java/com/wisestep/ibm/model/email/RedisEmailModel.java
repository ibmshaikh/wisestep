package com.wisestep.ibm.model.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("mailOTP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RedisEmailModel {

    @Id
    private String uid;
    private int OTP;
    private String email;
    private long otpExpiry;



}
