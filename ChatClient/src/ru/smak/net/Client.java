package ru.smak.net;

import ru.smak.ui.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private String host;
    private int port;
    private Socket s;
    private UI ui;
    private boolean stop;
    private ChatIO cio;
    public Client(String host, int port, UI ui){
        this.host = host;
        this.port = port;
        this.ui=ui;
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
                    ui.showMsg("Ошибка: " + e.getMessage());
                }
            }).start();
            ui.setDataReceiver((data)->{
                cio.sendMessage(data);
                return null;
            });
        } catch (IOException e) {
            ui.showMsg("Ошибка: " + e.getMessage());
        }
    }

    private Void parse(String msg){
        var data = msg.split(":", 2);
        try {
            var cmd = Command.valueOf(data[0]);
            switch (cmd){
                case INTRODUCE -> ui.showMsg(data[1]);
                case MESSAGE -> ui.showMsg(data[1]);
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
