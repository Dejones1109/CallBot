package com.its.sanve.api.communication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AbstractCommunication {

    @Value("${sanve.connectTimeout}")
    private long timeOut = 30;

    @Value("${sanve.readTimeout}")
    private long readOut = 60;
    
    @Value("${sanve.connection.poolSize}")
    private int poolSize = 10;

//    @Value("${sanve.token}")
//    private String token;

    protected OkHttpClient buildCommunication() {
        Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(8));
//        dispatcher.setMaxRequests(maxRequest);
//        dispatcher.setMaxRequestsPerHost(maxRequestPerHost);

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .addInterceptor(logger)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(readOut, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(poolSize, 60, TimeUnit.SECONDS)).
                build();

        return okHttpClient;
    }
}
