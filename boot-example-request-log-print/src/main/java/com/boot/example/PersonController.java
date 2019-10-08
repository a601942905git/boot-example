package com.boot.example;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.boot.example.PersonController
 *
 * @author lipeng
 * @date 2019/10/8 下午5:39
 */
@RestController
public class PersonController implements PersonApi{

    private static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new Person(10001, "test1"));
        personList.add(new Person(10002, "test2"));
        personList.add(new Person(10003, "test3"));
        personList.add(new Person(10004, "test4"));
        personList.add(new Person(10005, "test5"));
    }

    @Override
    public List<Person> list() {
        return personList;
    }

    @Override
    public Person get(Integer id) {
        Person defaultPerson = new Person(88888, "default");
        return personList.stream().filter(person -> Objects.equals(person.getId(), id)).findFirst().orElse(defaultPerson);
    }

    @Override
    public List<Person> add(Person person) {
        personList.add(person);
        return personList;
    }

    @Override
    public List<Person> update(Person person) {
        personList.removeIf(p -> Objects.equals(p.getId(), person.getId()));
        personList.add(person);
        return personList;
    }
}
