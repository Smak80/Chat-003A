package ru.smak;

import ru.smak.net.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        var s = new Server(5003);
        s.start();
        while (true){
            var cbr = new BufferedReader(new InputStreamReader(System.in));
            String userCommand = null;
            try {
                userCommand = cbr.readLine();
            } catch (IOException e) {
                System.out.println("Ошибка чтения данных с клавиатуры.");
            }
            if (userCommand == null || userCommand.equals("STOP")){
                s.stop();
                break;
            }
        }
    }
}
