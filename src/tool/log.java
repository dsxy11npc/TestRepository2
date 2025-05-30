package tool;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/** 日志类 */
public class log {
    //日志名与类同名
    private static final Logger logger= Logger.getLogger(log.class.getName());
    //静态初始化(一定执行的语句)
    static{
        //设置日志级别
        logger.setLevel(Level.ALL);
        try {
            //文件配置日志
            //初始化
            //文件名
            String name="socket.log";
            //大小限制
            int limit=1024*1024;
            //最大文件数量
            int count=10;
            //追加模式
            boolean append=true;
            //哈希表获取配置
            HashMap<String,String>logHash=file.getConfigHash();
            //遍历键值对
            for(String key:logHash.keySet()){
                //配上各个配置
                if(key.equals("logName")) name=logHash.get(key);
                else if(key.equals("logLimit")) limit=Integer.parseInt(logHash.get(key));
                else if(key.equals("logCount")) count=Integer.parseInt(logHash.get(key));
                else if(key.equals("logAppend")) append=Boolean.parseBoolean(logHash.get(key));
            }
            //日志处理器
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
