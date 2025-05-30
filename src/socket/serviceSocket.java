package socket;

import managerSystem.managerSystem;
import tool.log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/** 服务端类 */
public class serviceSocket implements Runnable{
    /** 服务端Socket */
    private ServerSocket serverSocket;
    /** 连接Socket */
    private Socket clientSocket;
    /** managerSystem层对象，用于开启接收指令使用 */
    private managerSystem MS;
    /** 连接方法 */
    public void linkService() throws IOException {
        /*
        分配内存给MS对象
        开启连接
         */
        MS=new managerSystem();
        serverSocket = new ServerSocket(MS.getPort());
        System.out.println("Service is start,waiting accept...");

        while (true) {
            //1.接受新连接
            clientSocket=serverSocket.accept();
            log.getLog().info("linked--to--"+clientSocket.getInetAddress());
            System.out.println("client linked: " + clientSocket.getInetAddress());
            //2.为每个客户端创建新线程
            Thread clientThread=new Thread(this);
            clientThread.start();
        }
    }

    /** 线程方法 */
    @Override
    public void run() {
        //开启IO读取指令
        try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ){
            while(true){
                // 接收客户端消息
                String received=reader.readLine();
                //判断是否断开连接
                if (received==null||received.equals("exit")){
                    System.out.println("client disconnected: " + clientSocket.getInetAddress());
                    break;
                }
                //打印用户指令
                System.out.println("client: "+received);

                //互斥锁
                synchronized (MS){
                    //发送回复
                    String response=MS.startSystem(received);
                    writer.println(response);
                    //结束标志(用于标记回复结束)
                    writer.println("end");
                }
            }
        } catch(IOException e){
            //记录日志
            log.getLog().severe("client exception"+e.getMessage());
        } finally{
            try{
                //关闭客户端套接字
                clientSocket.close();
            } catch(IOException e){
                //记录日志
                log.getLog().severe("close fail"+e.getMessage());
            }
        }
    }
}

