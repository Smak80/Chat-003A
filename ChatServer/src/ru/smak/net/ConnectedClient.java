package ru.smak.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ConnectedClient {
    private Socket s;
    private ChatIO cio;
    private String name = null;
    private static final ArrayList<ConnectedClient> clients = new ArrayList<>();
    public ConnectedClient(Socket client) throws IOException {
        s = client;
        clients.add(this);
        cio = new ChatIO(s);
        send(Command.INTRODUCE,"Введите своё имя");
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
        if(name != null){
            sendForAll(Command.MESSAGE, name + ": "+ msg);
        }
        else{
            boolean nameOK = true;
            for (var client: clients){
                if(msg.equals(client.name))
                {
                    send(Command.INTRODUCE, "Такое имя уже занято. Введите другое");
                    nameOK = false;
                    break;
                }
            }
            if(nameOK){
                name = msg;
                sendForAll(Command.MESSAGE, "Клиент "+ name + " вошел в чат");
            }
        }
        return null;
    }

    public void send(Command command, String msg){
        cio.sendMessage(command + ":" + msg);
    }

    public void sendForAll(Command command, String msg){
        for (var client: clients){
            if(client.name != null) {
                client.send(Command.MESSAGE, msg);
            }
        }
    }
}
