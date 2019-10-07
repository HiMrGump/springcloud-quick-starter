package com.project.common.db;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 这个类实现了数据库查询条件拼接工具类
 *
 * @ClassName: DBHelper
 * @Author WangQingYun
 * @Date Created in 2019/8/29 14:06
 * @Version 1.0
 **/
@Data
public class DBHelper {

    private List<FiledRelation> operationList = Lists.newArrayList();

    public static DBHelper build(){
        return new DBHelper();
    }

    public DBHelper addOperation(DBOperation operation,String filedName,Object...valueArr){
        if(operation == null || valueArr == null || StringUtils.isBlank(filedName)){
           throw new IllegalArgumentException("Operation type,Field name,Operation Values must be given.");
        }

        if(valueArr == null || valueArr.length == 0){
            return this;
        }

        if(DBOperation.BETWEEN != operation ){
            if(!isAdd(valueArr[0])){
                  return this;
            }
        }

        operationList.add(new FiledRelation(operation, filedName, valueArr));
        return this;
    }

    //是否不执行该拼接where条件操作,true加入,false拒绝
    private boolean isAdd(Object val1){
        if(val1 == null){
            return false;
        }
        if(val1.getClass().getName() == "java.lang.String"){
            String valStr1 = (String)val1;
            if(StringUtils.isBlank(valStr1) || "null".equalsIgnoreCase(valStr1) || "undefined".equalsIgnoreCase(valStr1)){
                return false;
            }
        }
        return true;
    }

    /**
     * 该接口定义了字段名、值、操作的映射关系
     */
    @Data
    @AllArgsConstructor
    public class FiledRelation{
        private DBOperation operation; //操作
        private String filedName; //字段名
        private Object[] value; //操作值
    }

}
