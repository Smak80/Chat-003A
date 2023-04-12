package ru.smak;

import ru.smak.net.Client;

public class Main {
    public static void main(String[] args) {
        new Client("localhost", 5003).start();
    }
}
