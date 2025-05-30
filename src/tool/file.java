package tool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

//文件功能--基于properties实现
public class file {

    private static database database_;
    private static HashMap<String,String>configHash;
    private static final String fileByConfig="config.properties";
    private static final String fileByHashString="hashString.properties";
    private static final String fileByHashLink="hashLink.properties";
    private static final String fileByHashHash="hashHash.properties";

    public file(database database){
        configHash=new HashMap<>();
        database_=database;
    }

    //加载文件
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
        Properties props_=new Properties();
        HashMap<String,String>tempHashString=new HashMap<>();
        try(FileInputStream in=new FileInputStream(fileByHashString)){
            props_.load(in);
            //遍历全部的键值对
            for(String keyProperties:props_.stringPropertyNames()){
                tempHashString.put(keyProperties,props_.getProperty(keyProperties));
            }
        }catch (IOException e){
            log.getLog().severe("读取数据失败"+e.getMessage());
        }
        database_.setHashString(tempHashString);
    }

    //分隔符读取
    public void loadFileByHashLink(){
        Properties props_=new Properties();
        HashMap<String,LinkedList<String>>tempHashLink=new HashMap<>();
        String delimiter="#";
        try(FileInputStream in=new FileInputStream(fileByHashLink)){
            props_.load(in);
            for(String keyProperties:props_.stringPropertyNames()){
                String tempValue=props_.getProperty(keyProperties);
                LinkedList<String>tempList=new LinkedList<>();
                for(String value:tempValue.split(delimiter)){
                    //去除空格
                    tempList.add(value.trim());
                }
                tempHashLink.put(keyProperties,tempList);
            }
        }catch (IOException e){
            log.getLog().severe("读取数据失败"+e.getMessage());
        }
        database_.setHashLink(tempHashLink);
    }

    public void loadFileByHashHash(){
        Properties props_=new Properties();
        HashMap<String,HashMap<String,String>>tempHashHash=new HashMap<>();
        String delimiter="#";
        try(FileInputStream in=new FileInputStream(fileByHashHash)){
            props_.load(in);
            //加载key
            for(String keyProperties:props_.stringPropertyNames()){
                String tempValue=props_.getProperty(keyProperties);
                HashMap<String,String>tempHash=new HashMap<>();
                String[] divKey=keyProperties.split(delimiter);
                tempHash.put(divKey[1],tempValue);
                tempHashHash.put(divKey[0],tempHash);
            }
        }catch (IOException e){
            log.getLog().severe("读取数据失败"+e.getMessage());
        }
        database_.setHashHash(tempHashHash);
    }

    public void saveHs(){
        Properties props_=new Properties();
        HashMap<String,String>hashString=database_.getHashString();
        //用set集合来获取key值
        if(!hashString.isEmpty()){
            Set<String>hashKeySet=hashString.keySet();
            for(String key:hashKeySet){
                props_.setProperty(key,hashString.get(key));
            }
        }
        try(FileOutputStream out=new FileOutputStream(fileByHashString)){
            props_.store(out,"HashString");
        }catch (IOException e){
            log.getLog().severe("保存异常"+e.getMessage());
        }
    }

    //list需要分隔
    public void saveHl(){
        Properties props_=new Properties();
        HashMap<String, LinkedList<String>>hashLink=database_.getHashLink();
        String delimiter="#";
        if(!hashLink.isEmpty()){
            for(String key:hashLink.keySet()){
                LinkedList<String>list=hashLink.get(key);
                //装入valueList
                String valueList=String.join(delimiter,list);
                props_.setProperty(key,valueList);
            }
        }
        try(FileOutputStream out=new FileOutputStream(fileByHashLink)){
            props_.store(out,"HashLink");
        }catch (IOException e){
            log.getLog().severe("保存异常"+e.getMessage());
        }
    }

    public void saveHh(){
        Properties props_=new Properties();
        HashMap<String,HashMap<String,String>>hashHash=database_.getHashHash();
        String delimiter="#";
        if(!hashHash.isEmpty()){
            for(String outKey:hashHash.keySet()){
                HashMap<String,String>innerHashHash=hashHash.get(outKey);
                for(String innerKey:innerHashHash.keySet()){
                    String keyHash=outKey+delimiter+innerKey;
                    props_.setProperty(keyHash,innerHashHash.get(innerKey));
                }
            }try(FileOutputStream out=new FileOutputStream(fileByHashHash)){
                props_.store(out,"HashHash");
            }catch (IOException e){
                log.getLog().severe("保存异常"+e.getMessage());
            }
        }
    }

    public static HashMap<String,String>getConfigHash(){
        return new HashMap<>(configHash);
    }
}
