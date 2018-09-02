package hello.controller;

import hello.domain.model.Person;
import hello.service.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Angelina
 */
@RestController
public class PersonController {

    @Autowired
    private ServiceData serviceData;

    @GetMapping("/getData")
    public List<Person> getData() {
      return serviceData.getData();
    }
}
