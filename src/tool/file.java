package tool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

/**
 * 文件类
 * 基于properties实现
 */
public class file {
    /** 数据库 */
    private static database database_;
    /** 存在配置数据用的哈希表 */
    private static HashMap<String,String>configHash;
    /** 各个文件的文件名 */
    private static final String fileByConfig="config.properties";
    private static final String fileByHashString="hashString.properties";
    private static final String fileByHashLink="hashLink.properties";
    private static final String fileByHashHash="hashHash.properties";
    /** 初始化 */
    public file(database database){
        configHash=new HashMap<>();
        database_=database;
    }

    /** 加载文件 */
    public void loadConfigHash(){
        Properties props_=new Properties();
        try(FileInputStream in=new FileInputStream(fileByConfig)){
            props_.load(in);
            for(String keyProperties:props_.stringPropertyNames()){
                configHash.put(keyProperties,props_.getProperty(keyProperties));
            }
        }catch (IOException e){
            log.getLog().severe("读取数据失败"+e.getMessage());
        }
    }

    public void loadFileByHashString(){
        //创建properties对象接收文件
        Properties props_=new Properties();
        //临时哈希表
        HashMap<String,String>tempHashString=new HashMap<>();
        try(FileInputStream in=new FileInputStream(fileByHashString)){
            //加载文件
            props_.load(in);
            //遍历全部的键值对
            for(String keyProperties:props_.stringPropertyNames()){
                //插入哈希表
                tempHashString.put(keyProperties,props_.getProperty(keyProperties));
            }
        }catch (IOException e){
            //记录日志
            log.getLog().severe("读取数据失败"+e.getMessage());
        }
        //将数据返回数据库
        database_.setHashString(tempHashString);
    }


    public void loadFileByHashLink(){
        //创建properties对象接收文件
        Properties props_=new Properties();
        //临时哈希表
        HashMap<String,LinkedList<String>>tempHashLink=new HashMap<>();
        //链表拼接需要分隔符读取
        String delimiter="#";
        try(FileInputStream in=new FileInputStream(fileByHashLink)){
            //加载文件
            props_.load(in);
            //遍历全部的键值对
            for(String keyProperties:props_.stringPropertyNames()){
                //链表所有的值拼接成的字符串
                String tempValue=props_.getProperty(keyProperties);
                //临时链表
                LinkedList<String>tempList=new LinkedList<>();
                //遍历所有值
                for(String value:tempValue.split(delimiter)){
                    //插入链表并且去除空格
                    tempList.add(value.trim());
                }
                //放入哈希表
                tempHashLink.put(keyProperties,tempList);
            }
        }catch (IOException e){
            //记录日志
            log.getLog().severe("读取数据失败"+e.getMessage());
        }
        //将数据返回数据库
        database_.setHashLink(tempHashLink);
    }

    public void loadFileByHashHash(){
        //创建properties对象接收文件
        Properties props_=new Properties();
        //临时哈希表
        HashMap<String,HashMap<String,String>>tempHashHash=new HashMap<>();
        //哈希标key拼接需要分隔符读取
        String delimiter="#";
        try(FileInputStream in=new FileInputStream(fileByHashHash)){
            //加载文件
            props_.load(in);
            //遍历全部的键值对
            for(String keyProperties:props_.stringPropertyNames()){
                //唯一value值
                String tempValue=props_.getProperty(keyProperties);
                //临时内层哈希表
                HashMap<String,String>tempHash=new HashMap<>();
                //分隔符分隔key
                String[] divKey=keyProperties.split(delimiter);
                //内层哈希表放入第二个key和唯一value
                tempHash.put(divKey[1],tempValue);
                //外层哈希表放入第一个key和内层哈希表
                tempHashHash.put(divKey[0],tempHash);
            }
        }catch (IOException e){
            //记录日志
            log.getLog().severe("读取数据失败"+e.getMessage());
        }
        //将数据返回数据库
        database_.setHashHash(tempHashHash);
    }
    /** 保存文件 */
    public void saveHs(){
        //创建properties对象接收文件
        Properties props_=new Properties();
        //利用数据库获取对应哈希表
        HashMap<String,String>hashString=database_.getHashString();
        if(!hashString.isEmpty()){
            //用set集合来获取key值
            Set<String>hashKeySet=hashString.keySet();
            //遍历所有的key
            for(String key:hashKeySet){
                //写入所有的键值对
                props_.setProperty(key,hashString.get(key));
            }
        }
        try(FileOutputStream out=new FileOutputStream(fileByHashString)){
            //存储文件
            props_.store(out,"HashString");
        }catch (IOException e){
            //记录日志
            log.getLog().severe("保存异常"+e.getMessage());
        }
    }

    //list需要分隔
    public void saveHl(){
        //创建properties对象接收文件
        Properties props_=new Properties();
        //利用数据库获取对应哈希表
        HashMap<String, LinkedList<String>>hashLink=database_.getHashLink();
        //分隔符拼接链表的值
        String delimiter="#";
        if(!hashLink.isEmpty()){
            //用set集合来获取key值
            for(String key:hashLink.keySet()){
                //临时链表获取哈希链表里的链表
                LinkedList<String>list=hashLink.get(key);
                //拼接分隔符和链表的值
                String valueList=String.join(delimiter,list);
                //写入所有的键值对
                props_.setProperty(key,valueList);
            }
        }
        try(FileOutputStream out=new FileOutputStream(fileByHashLink)){
            //存储文件
            props_.store(out,"HashLink");
        }catch (IOException e){
            //记录日志
            log.getLog().severe("保存异常"+e.getMessage());
        }
    }

    public void saveHh(){
        //创建properties对象接收文件
        Properties props_=new Properties();
        //利用数据库获取对应哈希表
        HashMap<String,HashMap<String,String>>hashHash=database_.getHashHash();
        //分隔符拼接链表的值
        String delimiter="#";
        if(!hashHash.isEmpty()){
            //用set集合来获取key值
            for(String outKey:hashHash.keySet()){
                //临时哈希表获取哈希哈希表里的内层哈希表
                HashMap<String,String>innerHashHash=hashHash.get(outKey);
                //遍历内层的key
                for(String innerKey:innerHashHash.keySet()){
                    //整合成一个key为外层key+分隔符+内层key
                    String keyHash=outKey+delimiter+innerKey;
                    //写入所有的键值对
                    props_.setProperty(keyHash,innerHashHash.get(innerKey));
                }
            }try(FileOutputStream out=new FileOutputStream(fileByHashHash)){
                //存储文件
                props_.store(out,"HashHash");
            }catch (IOException e){
                //记录日志
                log.getLog().severe("保存异常"+e.getMessage());
            }
        }
    }
    /** 返回配置文件的哈希表副本 */
    public static HashMap<String,String>getConfigHash(){
        return new HashMap<>(configHash);
    }
}
