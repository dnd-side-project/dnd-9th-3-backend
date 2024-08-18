package com.dnd.gooding.springconfig.redis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sentinel {

    private String host;
    private int port;
}
