package hello.service;

import hello.dao.MysqlData;
import hello.domain.model.Person;
import hello.util.JsonUtils;
import hello.util.RedisOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Angelina
 */
@Service("serviceData")
public class ServiceData {
    @Autowired
    private RedisOperations redisOperations;

    @Autowired
    private MysqlData mysqlData;


    public List<Person> getData() {

        List<Person> list;
        String value=redisOperations.get("ha");
        if(StringUtils.isEmpty(value)){
            //get the data from mysql
            list=mysqlData.queryPerson();
            redisOperations.set("ha", JsonUtils.objectToJson(list),100L);
            return list;
        }else {
            return JsonUtils.jsonToList(value,Person.class);
        }
    }

}
