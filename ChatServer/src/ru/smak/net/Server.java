package ru.smak.net;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private int port;
    private boolean stop;
    public Server(int port){
        this.port = port;
    }

    public void start(){
        ServerSocket ss;
        try {
            ss = new ServerSocket(port);
            stop = false;
            new Thread(()-> {
                while (!stop) {
                    try {
                        var s = ss.accept();
                        new ConnectedClient(s).start();
                    } catch (IOException e) {
                        System.out.println("Ошибка: "+e.getMessage());
                    }
                }
            }).start();
            ss.close();
        } catch (IOException e) {
            System.out.println("Ошибка: "+e.getMessage());
        }
    }

    public void stop(){
        stop = true;
    }
}
