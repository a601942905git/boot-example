package com.boot.example;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.boot.example.PersonController
 *
 * @author lipeng
 * @date 2019/10/21 下午8:16
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private static List<Person> personList = new ArrayList<>();

    static {
        personList.add(new Person(100001, "jack", 22));
        personList.add(new Person(100002, "lucky", 23));
        personList.add(new Person(100003, "jone", 24));
        personList.add(new Person(100004, "lucy", 25));
        personList.add(new Person(100005, "lily", 26));
    }

    @GetMapping("/")
    public List<Person> listPerson() {
        return personList;
    }

    @GetMapping("id/{id}")
    public Person getPerson(@PathVariable("id") Integer id) {
        return personList.stream()
                .filter(person -> Objects.equals(person.getPersonId(), id))
                .findFirst()
                .orElse(new Person(999999, "default", 18));
    }

    @PostMapping("/")
    public List<Person> addPerson(@RequestBody Person person) {
        personList.add(person);
        return personList;
    }

    @PutMapping("/")
    public List<Person> updatePerson(@RequestBody Person person) {
        personList.removeIf(p -> Objects.equals(p.getPersonId(), person.getPersonId()));
        personList.add(person);
        return personList;
    }
}
