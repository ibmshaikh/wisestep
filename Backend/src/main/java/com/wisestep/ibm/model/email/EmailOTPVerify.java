package com.wisestep.ibm.model.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailOTPVerify {

    private String email;
    private int otp;
    private String uid;

}
