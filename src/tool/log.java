package tool;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class log {
    //与类同名
    private static final Logger logger= Logger.getLogger(log.class.getName());
    //静态初始化
    static{
        logger.setLevel(Level.ALL);//设置日志级别
        try {
            //文件配置日志
            //初始化
            String name="socket.log";
            int limit=1024*1024;
            int count=10;
            boolean append=true;
            HashMap<String,String>logHash=file.getConfigHash();
            for(String key:logHash.keySet()){
                if(key.equals("logName")) name=logHash.get(key);
                else if(key.equals("logLimit")) limit=Integer.parseInt(logHash.get(key));
                else if(key.equals("logCount")) count=Integer.parseInt(logHash.get(key));
                else if(key.equals("logAppend")) append=Boolean.parseBoolean(logHash.get(key));
            }
            FileHandler fileHandler=new FileHandler(name,limit,count,append);
            fileHandler.setFormatter(new SimpleFormatter());//日志格式
            logger.addHandler(fileHandler);//添加到日志记录器
        } catch (IOException e) {
            System.out.println("logging exception!"+e.getMessage());
        }
    }
    //返回实例
    public static Logger getLog(){
        return logger;
    }
}
