package ru.smak.ui;

import java.util.function.Function;

public interface UI {
    void setDataReceiver(Function<String, Void> receiver);
    void showMsg(String msg);
    void showComm(String comm);
}
