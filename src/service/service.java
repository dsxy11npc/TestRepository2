package service;

import tool.database;
import tool.log;

/**
 * 服务类
 */
public class service {
    /** 工具层数据库 */
    private database database_;
    /** 初始化 */
    public service(database database){
        database_=database;
    }

    /**
     * 响应各种指令方法
     * 调用数据库控制存储
     */
    public String toSet(String key,String value){
        database_.set(key,value);
        return "1";
    }

    public String toGet(String key){
        return database_.get(key);
    }

    public String toDel(String key){
        return database_.del(key);
    }

    public String toLpush(String key,String value){
        database_.lpush(key,value);
        return "1";
    }

    public String toRpush(String key,String value){
        database_.rpush(key,value);
        return "1";
    }

    public String toRange(String key,String start,String end){
        if(database_.isNumeric(start)&&database_.isNumeric(end)){
            //判断range方法的边界条件
            if(Integer.parseInt(start)>Integer.parseInt(end)){
                //记录日志
                log.getLog().info("start must be less than end!");
                return "start must be less than end!";
            }
            else
                return database_.range(key,Integer.parseInt(start),Integer.parseInt(end));
        }
        else{
            //记录日志
            log.getLog().info("Input is illegal(start and end must be integer)");
            //range方法必须输入数字边界
            return "Input is illegal(start and end must be integer)";
        }
    }

    public String toLen(String key){
        return database_.len(key);
    }

    public String toLpop(String key){
        return database_.lpop(key);
    }

    public String toRpop(String key){
        return database_.rpop(key);
    }

    public String toLdel(String key){
        return database_.ldel(key);
    }

    public String toHset(String key1,String key2,String value){
        database_.hset(key1,key2,value);
        return "1";
    }

    public String toHget(String key1,String key2){
        return database_.hget(key1,key2);
    }

    public String toHdel(String key1,String key2){
        return database_.hdel(key1,key2);
    }

    /**
     * 响应各种HELP指令
     */
    public String toPing(){
        return "PONG";
    }

    public String toHelpSet(){
        return "SET [KEY] [VALUE]";
    }

    public String toHelpRpop(){
        return "RPOP [KEY]";
    }

    public String toHelpLen(){
        return "LEN [KEY]";
    }

    public String toHelpRange(){
        return "RANGE [KEY] [START] END]";
    }

    public String toHelpHelp(){
        return "HELP [COMMAND]";
    }

    public String toHelpHset(){
        return "HSET [KEY1] [VALUE1] [KEY2] [VALUE2]...";
    }

    public String toHelpDel(){
        return "DEL [KEY] [KEY1] [KEY2]";
    }

    public String toHelpRpush(){
        return "RPUSH [KEY] [VALUE]";
    }

    public String toHelpLpop(){
        return "LPOP [KEY]";
    }

    public String toHelpLdel(){
        return "LDEL [KEY]";
    }

    public String toHelpHget(){
        return "HGET [KEY1] [KEY2]...";
    }

    public String toHelpGet(){
        return "GET [KEY] LPUSH [KEY] [VALUE]";
    }

    public String toHelpLpush(){
        return "LPUSH [KEY] [VALUE]";
    }

    public String toHelpHdel(){
        return "HDEL [KEY1] [KEY2]...";
    }

    public String toHelpAll(){
        return """
                RPOP [KEY]
                LEN [KEY]
                RANGE [KEY] [START] [END]\
                
                HELP [COMMAND]
                HSET [KEY1] [VALUE1] [KEY2] [VALUE2]...\
                
                SET [KEY] [VALUE]
                DEL [KEY] [KEY1] [KEY2]
                HDEL [KEY1] [KEY2]...\
                
                RPUSH [KEY] [VALUE]
                LPOP [KEY]
                LDEL [KEY]\
                
                HGET [KEY1] [KEY2]...
                GET [KEY]
                LPUSH [KEY] [VALUE]\
                
                EXIT""";
    }
    /** 指令不存在方法 */
    public String noCommand(){
        //记录日志
        log.getLog().info("no this command!");
        return "no this command!";
    }

}

