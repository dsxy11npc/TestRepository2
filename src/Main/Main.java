package Main;
import socket.serviceSocket;
import java.io.IOException;

/** 服务端开启主方法 */
public class Main {
    public static void main(String[] args) throws IOException {
        //创建socket层serviceSocket对象启动服务端
        serviceSocket startSocket=new serviceSocket();
        startSocket.linkService();
    }

}
