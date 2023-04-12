package ru.smak.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private String host;
    private int port;
    private Socket s;
    private boolean stop;
    private ChatIO cio;
    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void start(){
        stop = false;
        try {
            s = new Socket(host, port);
            cio = new ChatIO(s);
            new Thread(()->{
                try {
                    cio.startReceiving(this::parse);
                } catch (IOException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }).start();
            var cbr = new BufferedReader(new InputStreamReader(System.in));
            while (!stop){
                cio.sendMessage(cbr.readLine());
            }
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Void parse(String msg){
        var data = msg.split(":", 2);
        try {
            var cmd = Command.valueOf(data[0]);
            switch (cmd){
                case INTRODUCE -> System.out.println(data[1]);
                case MESSAGE -> System.out.println(data[1]);
            }
        }
        catch (Exception e){
        }
        return null;
    }

    public void stop(){
        stop = true;
        cio.stopReceiving();
    }
}
