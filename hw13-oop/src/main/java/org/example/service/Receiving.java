package org.example.service;

//принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
public interface Receiving {
    void receiveOne(Integer amount);

    void receiveTen(Integer amount);

    void receiveFifty(Integer amount);

    void receiveHundred(Integer amount);

    void receiveFiveHundred(Integer amount);

    void receiveThousand(Integer amount);
}
