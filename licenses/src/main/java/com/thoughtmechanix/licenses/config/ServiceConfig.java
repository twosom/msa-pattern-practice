package com.thoughtmechanix.licenses.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "example")
public class ServiceConfig {
    private String property;
}
