package com.codef.api.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt.token")
@Getter
@Setter
@ToString
public class JwtConfiguration {

    private String header;

    private String issuer;

    private String serverKey;

    private int expirySeconds;

}