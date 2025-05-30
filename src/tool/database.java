package tool;

import java.util.HashMap;
import java.util.LinkedList;

//存放数据功能
public class database {
    private static HashMap<String,String>hashString_;
    private static HashMap<String, LinkedList<String>>hashLink_;
    private static HashMap<String,HashMap<String,String>>hashHash_;
    private static file file_;

    public database(){
        hashString_=new HashMap<>();
        hashLink_=new HashMap<>();
        hashHash_=new HashMap<>();
        file_=new file(this);//传入当前数据库给file
        //读取全部数据
        file_.loadConfigHash();
        file_.loadFileByHashString();
        file_.loadFileByHashLink();
        file_.loadFileByHashHash();
    }
    //响应各个指令
    public void set(String key,String value){
        hashString_.put(key,value);
        file_.saveHs();
    }

    public String get(String key){
        return hashString_.get(key);
    }

    public String del(String key){
        if(hashString_==null||hashString_.isEmpty()){
            log.getLog().info("(del)client:"+key+"--no any value");
            return "no any value";
        }
        hashString_.remove(key);
        file_.saveHs();
        return "1";
    }

    public void lpush(String key,String value){
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null) tempLink=new LinkedList<>();
        tempLink.addFirst(value);
        hashLink_.put(key,tempLink);
        file_.saveHl();
    }

    public void rpush(String key,String value){
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null) tempLink=new LinkedList<>();
        tempLink.addLast(value);
        hashLink_.put(key,tempLink);
        file_.saveHl();
    }

    public String range(String key,int start,int end){
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null||tempLink.isEmpty()){
            log.getLog().info("(range)client:"+key+"--no any value");
            return "no any value!";
        }else if(start>=tempLink.size()||end>=tempLink.size()){
            log.getLog().info("(range)client:"+start+" "+end
                    +"out of bound(max size is "+(tempLink.size()-1)+")");
            return "out of bound(max size is "+(tempLink.size()-1)+")";
        }
        StringBuilder reStr= new StringBuilder();
        for(int i=start;i<=end;i++){
            reStr.append(tempLink.get(i));
            reStr.append(" ");
        }
        return reStr.toString();
    }

    public String len(String key){
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null){
            return "0";
        }else return Integer.toString(tempLink.size());
    }

    public String lpop(String key){
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null||tempLink.isEmpty()){
            log.getLog().info("(lpop)client:"+key+"--no this key!");
            return "no this key!";
        }
        String ans=tempLink.getFirst();
        tempLink.removeFirst();
        file_.saveHl();
        return ans;
    }

    public String rpop(String key){
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null||tempLink.isEmpty()){
            log.getLog().info("(rpop)client:"+key+"--no this key!");
            return "no this key!";
        }
        String ans=tempLink.getLast();
        tempLink.removeLast();
        file_.saveHl();
        return ans;
    }

    public String ldel(String key){
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null){
            log.getLog().info("(ldel)client:"+key+"--no this key!");
            return "no this key!";
        }else{
            tempLink.clear();
            file_.saveHl();
            return "1";
        }
    }

    public void hset(String key1,String key2,String value){
        HashMap<String,String>tempHash;
        tempHash=hashHash_.get(key1);
        if(tempHash==null){
            tempHash=new HashMap<>();
        }
        tempHash.put(key2,value);
        hashHash_.put(key1,tempHash);
        file_.saveHh();
    }

    public String hget(String key1,String key2){
        HashMap<String,String>tempHash;
        tempHash=hashHash_.get(key1);
        if(tempHash==null||tempHash.isEmpty()){
            log.getLog().info("(hget)client:"+key1+"--no this key!");
            return "no this key!";
        }else{
            return tempHash.get(key2);
        }
    }

    public String hdel(String key1,String key2){
        HashMap<String,String>tempHash;
        tempHash=hashHash_.get(key1);
        if(tempHash==null||tempHash.isEmpty()){
            log.getLog().info("(hdel)client:"+key1+"--no this key!");
            return "no this key!";
        }else{
            tempHash.remove(key2);
            file_.saveHh();
            return "1";
        }
    }

    //辅助函数判断是否能强转数字
    public boolean isNumeric(String str) {
        if (str == null) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //获取各个哈希表的副本
    public HashMap<String,String> getHashString(){
        return new HashMap<>(hashString_);
    }
    public HashMap<String,LinkedList<String>> getHashLink(){
        return new HashMap<>(hashLink_);
    }
    public HashMap<String,HashMap<String,String>> getHashHash(){
        return new HashMap<>(hashHash_);
    }
    //设置各个哈希表
    public void setHashString(HashMap<String,String>hashSetInString){
        hashString_=hashSetInString;
    }
    public void setHashLink(HashMap<String,LinkedList<String>>hashSetInLink){
        hashLink_=hashSetInLink;
    }
    public void setHashHash(HashMap<String,HashMap<String,String>>hashSetInHash){
        hashHash_=hashSetInHash;
    }

    public int getPort(){
        HashMap<String,String>findConfigPort=file_.getConfigHash();
        return Integer.parseInt(findConfigPort.get("PORT"));
    }

}
