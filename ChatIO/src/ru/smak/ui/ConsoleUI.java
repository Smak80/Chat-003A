package ru.smak.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

public class ConsoleUI implements UI{
    private boolean stop=false;
    private BufferedReader br;
    public ConsoleUI() {
        br=new BufferedReader(new InputStreamReader(System.in));
    }
    private Function<String, Void> receiver=null;
    public void start(){

        stop = false;
        while (!stop){
            String msg = null;
            try {
                msg = br.readLine();
                if(receiver!=null)
                    receiver.apply(msg);
            } catch (IOException e) {

            }

        }
    }

    @Override
    public void setDataReceiver(Function<String, Void> receiver) {
        this.receiver=receiver;
    }

    @Override
    public void showMsg(String msg) {
        System.out.println(msg);

    }

    @Override
    public void showComm(String comm) {
        System.out.println(comm);

    }
}
