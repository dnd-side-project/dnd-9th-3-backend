package com.dnd.gooding.springconfig.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "redis")
public class SentinelProperties {

    private List<Sentinel> sentinels;
}
