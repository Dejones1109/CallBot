package com.its.sanve.api.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component

public class Client {
    String apiKey;
    String secretKey;
    String creat_at;
}
