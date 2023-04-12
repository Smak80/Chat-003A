package ru.smak;

import ru.smak.net.Client;
import ru.smak.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        var ui=new ConsoleUI();
        new Thread(ui::start).start();
        new Client("localhost", 5003,ui).start();
    }
}
