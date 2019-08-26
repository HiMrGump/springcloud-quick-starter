package com.project.common.db;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 这个类实现了XXXX相关功能
 *
 * @Author Gump
 * @Date 2019/8/2410:08
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
           throw new IllegalArgumentException("操作类型,字段名,操作值必填");
        }

        if(valueArr == null || valueArr.length == 0){
            return this;
        }

        operationList.add(new FiledRelation(operation, filedName, valueArr));
        return this;
    }

    @Data
    @AllArgsConstructor
    public class FiledRelation{
        private DBOperation operation; //操作
        private String filedName; //字段名
        private Object[] value; //操作值
    }

}
