package com.its.sanve.api.utils;




import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
@Getter
@Setter
@Component
public class ClientManager {
    private List<Client> clients;

    public Client getClient(String clientID) {
        for (Client client : clients) {
            if (client.getApiKey().equalsIgnoreCase(clientID)) {
                return client;
            }
            return null;
        }
        return null;
    }
}
