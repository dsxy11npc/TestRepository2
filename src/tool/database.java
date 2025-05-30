package tool;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 模拟数据库类
 */
public class database {
    /** 存放哈希字符串数据 */
    private static HashMap<String,String>hashString_;
    /** 存放哈希链表数据 */
    private static HashMap<String, LinkedList<String>>hashLink_;
    /** 存放哈希哈希表数据 */
    private static HashMap<String,HashMap<String,String>>hashHash_;
    /** 文件操作对象 */
    private static file file_;
    /** 初始化 */
    public database(){
        //分配空间
        hashString_=new HashMap<>();
        hashLink_=new HashMap<>();
        hashHash_=new HashMap<>();
        //传入当前数据库给file
        file_=new file(this);
        //加载全部数据
        file_.loadConfigHash();
        file_.loadFileByHashString();
        file_.loadFileByHashLink();
        file_.loadFileByHashHash();
    }
    /**
     * 响应各个指令
     * 并且对应各种哈希表的操作
     */
    public void set(String key,String value){
        hashString_.put(key,value);
        file_.saveHs();
    }
    /** 获取对应值 */
    public String get(String key){
        return hashString_.get(key);
    }
    /** 删除对应值 */
    public String del(String key){
        //为空的时候返回为空的响应
        if(hashString_==null||hashString_.isEmpty()){
            //记录日志
            log.getLog().info("(del)client:"+key+"--no any value");
            return "no any value";
        }
        //删除
        hashString_.remove(key);
        //保存文件
        file_.saveHs();
        return "1";
    }
    /** 左插 */
    public void lpush(String key,String value){
        //临时链表对应指令key值的链表
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null) tempLink=new LinkedList<>();
        //头插法
        tempLink.addFirst(value);
        hashLink_.put(key,tempLink);
        //保存文件
        file_.saveHl();
    }
    /** 右插 */
    public void rpush(String key,String value){
        //临时链表对应指令key值的链表
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        if(tempLink==null) tempLink=new LinkedList<>();
        //尾插法
        tempLink.addLast(value);
        hashLink_.put(key,tempLink);
        //保存文件
        file_.saveHl();
    }
    /** 获取区间的值 */
    public String range(String key,int start,int end){
        //临时链表对应指令key值的链表
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        //为空特判
        if(tempLink==null||tempLink.isEmpty()){
            //记录日志
            log.getLog().info("(range)client:"+key+"--no any value");
            return "no any value!";
        }//下标越界特判
        else if(start>=tempLink.size()||end>=tempLink.size()){
            //记录日志
            log.getLog().info("(range)client:"+start+" "+end
                    +"out of bound(max size is "+(tempLink.size()-1)+")");
            return "out of bound(max size is "+(tempLink.size()-1)+")";
        }//创建完整字符串返回
        StringBuilder reStr= new StringBuilder();
        for(int i=start;i<=end;i++){
            reStr.append(tempLink.get(i));
            reStr.append(" ");
        }
        return reStr.toString();
    }
    /** 获取长度 */
    public String len(String key){
        //临时链表对应指令key值的链表
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        //为空返回0
        if(tempLink==null){
            return "0";
        }else return Integer.toString(tempLink.size());
    }
    /** 左删 */
    public String lpop(String key){
        //临时链表对应指令key值的链表
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        //为空特判
        if(tempLink==null||tempLink.isEmpty()){
            //记录日志
            log.getLog().info("(lpop)client:"+key+"--no this key!");
            return "no this key!";
        }
        //删除左数并且返回
        String ans=tempLink.getFirst();
        tempLink.removeFirst();
        //保存文件
        file_.saveHl();
        return ans;
    }
    /** 右删 */
    public String rpop(String key){
        //临时链表对应指令key值的链表
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        //为空特判
        if(tempLink==null||tempLink.isEmpty()){
            //记录日志
            log.getLog().info("(rpop)client:"+key+"--no this key!");
            return "no this key!";
        }
        //删除右数并且返回
        String ans=tempLink.getLast();
        tempLink.removeLast();
        //保存文件
        file_.saveHl();
        return ans;
    }
    /** 全部删除 */
    public String ldel(String key){
        //临时链表对应指令key值的链表
        LinkedList<String>tempLink;
        tempLink=hashLink_.get(key);
        //为空特判
        if(tempLink==null){
            //记录日志
            log.getLog().info("(ldel)client:"+key+"--no this key!");
            return "no this key!";
        }else{
            //清空链表
            tempLink.clear();
            //保存文件
            file_.saveHl();
            return "1";
        }
    }
    /** 哈希设置value */
    public void hset(String key1,String key2,String value){
        //临时哈希表对应指令key值的链表
        HashMap<String,String>tempHash;
        tempHash=hashHash_.get(key1);
        //为空特判
        if(tempHash==null){
            tempHash=new HashMap<>();
        }
        tempHash.put(key2,value);
        hashHash_.put(key1,tempHash);
        //保存文件
        file_.saveHh();
    }
    /** 获取对应value值 */
    public String hget(String key1,String key2){
        //临时哈希表对应指令key值的链表
        HashMap<String,String>tempHash;
        tempHash=hashHash_.get(key1);
        //为空特判
        if(tempHash==null||tempHash.isEmpty()){
            //记录日志
            log.getLog().info("(hget)client:"+key1+"--no this key!");
            return "no this key!";
        }else{
            return tempHash.get(key2);
        }
    }
    /** 删除对应value值 */
    public String hdel(String key1,String key2){
        //临时哈希表对应指令key值的链表
        HashMap<String,String>tempHash;
        tempHash=hashHash_.get(key1);
        //为空特判
        if(tempHash==null||tempHash.isEmpty()){
            //记录日志
            log.getLog().info("(hdel)client:"+key1+"--no this key!");
            return "no this key!";
        }else{
            tempHash.remove(key2);
            //保存文件
            file_.saveHh();
            return "1";
        }
    }

    /** 辅助方法判断是否能强转数字 */
    public boolean isNumeric(String str) {
        //为空特判
        if (str == null) return false;
        try {
            //被捕获到代表输入的非数字
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     *Getter
     * Setter
     */
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
    /** 获取端口 */
    public int getPort(){
        HashMap<String,String>findConfigPort=file_.getConfigHash();
        return Integer.parseInt(findConfigPort.get("PORT"));
    }

}
