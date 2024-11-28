package com.sayedbaladoh.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties("security.jwt")
@Getter
@Setter
public class JwtProperties {
    private String secretKey;
    private Duration tokenDuration;
}