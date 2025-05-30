package socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/** 客户端类 */
public class client {
    /** 启动 */
    public static void main(String[] args) throws IOException {
        //套接字
        Socket socket_;
        //建立连接的基本信息
        socket_=new Socket("127.0.0.1",8080);
        //交互输入
        Scanner scan=new Scanner(System.in);

        //输入输出IO
        InputStream is=socket_.getInputStream();
        OutputStream os=socket_.getOutputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        PrintWriter writer=new PrintWriter(os, true);

        try{
            while(true){
                //发送数据
                String response;
                System.out.print(socket_.getInetAddress()+">");
                response=scan.nextLine();
                //输入exit断开连接
                if (response.equals("exit")) {
                    break;
                }
                writer.println(response);

                //读取数据
                String line;
                while(!(line=reader.readLine()).isEmpty()){
                    if(line.equals("end")){//结束标志
                        break;
                    }
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("连接异常: " + e.getMessage());
        } finally {
            //关闭套接字
            socket_.close();
        }
    }
}
