package com.example.sharedmemoryproblemresearch.protocol;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "protocol.server")
@Component
@Getter
@Setter
public class ProtocolProperties {
    private String type;
    private String address;
    private Integer port;
}
