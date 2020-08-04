package com.its.sanve.api.communication;

import javax.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Log4j2
@Component
public class CallBotCommunication extends AbstractCommunication {

    @Value("${sanve.endpoint}")
    private String baseUrl;

    private SanVeCommunicate botCommunicate;

//    @Autowired
//    private ObjectMapper objectMapper;

    @PostConstruct
    public void intConnection() {
        this.botCommunicate = buildSetting();
    }

    private SanVeCommunicate buildSetting() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(this.buildCommunication())
                .build();

        return retrofit.create(SanVeCommunicate.class);
    }
    
    


}
