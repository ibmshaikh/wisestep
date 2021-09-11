package com.wisestep.ibm.model.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("otp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailOTPConfig {
    private int expiry;
}
