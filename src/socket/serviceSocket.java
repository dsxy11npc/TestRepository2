package socket;

import managerSystem.managerSystem;
import tool.log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//客户端，负责连接
public class serviceSocket implements Runnable{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private managerSystem MS;

    public void linkService() throws IOException {
        MS=new managerSystem();
        serverSocket = new ServerSocket(MS.getPort());
        System.out.println("Service is start,waiting accept...");

        while (true) {
            // 1. 接受新连接（主线程）
            clientSocket=serverSocket.accept();
            log.getLog().info("linked--to--"+clientSocket.getInetAddress());
            System.out.println("client linked: " + clientSocket.getInetAddress());

            // 2. 为每个客户端创建新线程（交互逻辑在 run()）
            Thread clientThread=new Thread(this);
            clientThread.start();
        }
    }

    @Override
    public void run() {
        try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ){
            while(true){
                // 接收客户端消息
                String received=reader.readLine();
                if (received==null||received.equals("exit")){
                    System.out.println("client disconnected: " + clientSocket.getInetAddress());
                    break;
                }
                //ip  clientSocket.getInetAddress()
                System.out.println("client: "+received);

                //发送回复
                //互斥锁
                synchronized (MS){
                    String response=MS.startSystem(received);
                    writer.println(response);
                    writer.println("end");//结束标志
                }
            }
        } catch(IOException e){
            log.getLog().severe("client exception"+e.getMessage());
        } finally{
            try{
                clientSocket.close();
            } catch(IOException e){
                log.getLog().severe("close fail"+e.getMessage());
            }
        }
    }
}

