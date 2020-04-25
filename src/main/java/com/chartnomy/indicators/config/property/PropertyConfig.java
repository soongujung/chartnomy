package com.chartnomy.indicators.config.property;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/connection.properties")
public class PropertyConfig {
}
