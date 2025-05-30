package control;

import service.service;

/**
 * 控制器类
 */
public class control {
    /** 服务层实体对象 */
    private static service service_;
    /** 初始化 */
    public control(service service){
        service_=service;
    }

    /** 处理用户的请求并且发送到对应的service层 */
    public String dealRequire(String command){
        /*
        用空格分隔字符串
        根据不同指令对应不同service层函数
         */
        String[] cmd=command.split(" ");
        if(cmd[0].equals("set")){
            if(cmd.length==3){
                return service_.toSet(cmd[1],cmd[2]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("get")){
            if(cmd.length==2){
                return service_.toGet(cmd[1]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("del")){
            if(cmd.length==2){
                return service_.toDel(cmd[1]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("lpush")){
            if(cmd.length==3){
                return service_.toLpush(cmd[1],cmd[2]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("rpush")){
            if(cmd.length==3){
                return service_.toRpush(cmd[1],cmd[2]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("range")){
            if(cmd.length==4){
                return service_.toRange(cmd[1],cmd[2],cmd[3]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("len")){
            if(cmd.length==2){
                return service_.toLen(cmd[1]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("lpop")){
            if(cmd.length==2){
                return service_.toLpop(cmd[1]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("rpop")){
            if(cmd.length==2){
                return service_.toRpop(cmd[1]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("ldel")){
            if(cmd.length==2){
                return service_.toLdel(cmd[1]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("hset")){
            if(cmd.length==4){
                return service_.toHset(cmd[1],cmd[2],cmd[3]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("hget")){
            if(cmd.length==3){
                return service_.toHget(cmd[1],cmd[2]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("hdel")){
            if(cmd.length==3){
                return service_.toHdel(cmd[1],cmd[2]);
            }else{
                return service_.noCommand();
            }
        }else if(cmd[0].equals("help")){
            if(cmd.length==1){
                return service_.toHelpAll();
            }
            else if(cmd[1].equals("set")){
                return service_.toHelpSet();
            }
            else if(cmd[1].equals("get")){
                return service_.toHelpGet();
            }else if(cmd[1].equals("del")){
                return service_.toHelpDel();
            }else if(cmd[1].equals("lpush")){
                return service_.toHelpLpush();
            }else if(cmd[1].equals("rpush")){
                return service_.toHelpRpush();
            }else if(cmd[1].equals("range")){
                return service_.toHelpRange();
            }else if(cmd[1].equals("len")){
                return service_.toHelpLen();
            }else if(cmd[1].equals("lpop")){
                return service_.toHelpLpop();
            }else if(cmd[1].equals("rpop")){
                return service_.toHelpRpop();
            }else if(cmd[1].equals("ldel")){
                return service_.toHelpLdel();
            }else if(cmd[1].equals("hset")){
                return service_.toHelpHset();
            }else if(cmd[1].equals("hget")){
                return service_.toHelpHget();
            }else if(cmd[1].equals("hdel")){
                return service_.toHelpHdel();
            }else if(cmd[1].equals("help")){
                return service_.toHelpHelp();
            }
        }else if(cmd[0].equals("ping")){
            return service_.toPing();
        }
        else{
            return service_.noCommand();
        }
        return "";
    }
}
