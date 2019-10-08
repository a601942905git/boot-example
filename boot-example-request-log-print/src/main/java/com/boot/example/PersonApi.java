package com.boot.example;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * com.boot.example.PersonApi
 *
 * @author lipeng
 * @date 2019/9/26 上午11:38
 */
@RequestMapping("/persons")
public interface PersonApi {

    /**
     * list
     *
     * @return
     */
    @GetMapping("/")
    List<Person> list();

    /**
     * get
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Person get(@PathVariable("id") Integer id);

    /**
     * add
     *
     * @param person
     * @return
     */
    @PostMapping("/")
    List<Person> add(@Valid @RequestBody Person person);

    /**
     * update
     *
     * @param person
     * @return
     */
    @PutMapping("/")
    List<Person> update(@Valid @RequestBody Person person);
}
