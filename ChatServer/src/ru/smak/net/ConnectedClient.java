package ru.smak.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectedClient {
    private Socket s;
    private ChatIO cio;
    private static final ArrayList<ConnectedClient> clients = new ArrayList<>();
    public ConnectedClient(Socket client) throws IOException {
        s = client;
        clients.add(this);
        cio = new ChatIO(s);
    }

    public void start(){
        new Thread(()->{
            try {
                cio.startReceiving(this::parse);
            } catch (IOException e) {
                System.out.println("Ошибка: "+e.getMessage());
            }
        }).start();
    }

    private Void parse(String msg){
        System.out.println(msg);
        return null;
    }
}
