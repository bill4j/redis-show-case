package hello.dao;

import hello.domain.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelina
 */
@Component
public class MysqlData {

    public List<Person> queryPerson() {
        //imitate the real situation, get the data from DB
        List<Person> list = new ArrayList<>();
        Person person = new Person("1", "9023");
        Person person1 = new Person("ilks", "your");
        list.add(person);
        list.add(person1);
        return list;
    }
}
