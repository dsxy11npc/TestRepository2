package managerSystem;

//管理调度整个系统
import service.service;
import control.control;
import tool.database;
import socket.serviceSocket;
import tool.file;

import java.io.IOException;
import java.util.Scanner;

public class managerSystem {

    private static control control_;
    private static database database_;

    public managerSystem(){
        database_ = new database();
        service service_ = new service(database_);
        control_=new control(service_);
    }

    public String startSystem(String str) throws IOException {
        return control_.dealRequire(str);
    }
    //获取端口
    public int getPort(){
        return database_.getPort();
    }

}

