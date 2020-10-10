package src.main.java.com.its.sanve.api.communication.callbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Log4j2

@NoArgsConstructor
@AllArgsConstructor
public class SocketIO {
    public Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        log.info("connected!");
    }

    public String sendMessage(String msg) throws IOException {
        log.info("Send Message:{}", msg);
        out.println(msg);
        String resp = in.readLine();
        log.info("Recevived reponse:{}", resp);
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
