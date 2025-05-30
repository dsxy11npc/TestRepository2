package managerSystem;
import service.service;
import control.control;
import tool.database;
import java.io.IOException;

/** 管理整个系统类 */
public class managerSystem {
    /** 控制层控制器对象 */
    private static control control_;
    /** 工具层数据库对象 */
    private static database database_;
    /** 初始化 */
    public managerSystem(){
        database_ = new database();
        service service_ = new service(database_);
        control_=new control(service_);
    }
    /** 接收指令方法 */
    public String startSystem(String str) throws IOException {
        return control_.dealRequire(str);
    }
    /** 获取端口号方法 */
    public int getPort(){
        return database_.getPort();
    }

}

