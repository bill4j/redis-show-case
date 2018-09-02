package hello.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Angelina
 */
@Data
@NoArgsConstructor
public class Person implements Serializable{
    private static final long serialVersionUID = 4660766934837814357L;
    private String id;
    private String name;

    public Person(String id,String name){
        this.id=id;
        this.name=name;
    }
}