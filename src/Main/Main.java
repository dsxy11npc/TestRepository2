package Main;

//主程序运行

import socket.serviceSocket;
import tool.log;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        serviceSocket startSocket=new serviceSocket();
        startSocket.linkService();
    }

}
