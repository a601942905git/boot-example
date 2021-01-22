package com.boot.example.controller;

import com.boot.example.entity.User;
import com.boot.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.controller.UserController
 *
 * @author lipeng
 * @date 2020/12/23 10:21 AM
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public List<User> listUser() {
        return userService.listUser();
    }

    @GetMapping("/deferredResultUser")
    public DeferredResult<ResponseEntity<List<User>>> deferredResultListUser() {
        DeferredResult<ResponseEntity<List<User>>> deferredResult =
                new DeferredResult<>(20000L, new ResponseEntity<>(HttpStatus.NOT_MODIFIED));
        deferredResult.onTimeout(() -> {
            log.info("调用超时");
        });

        deferredResult.onCompletion(() -> {
            log.info("调用完成");
        });

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                deferredResult.setResult(new ResponseEntity<>(userService.listUser(), HttpStatus.OK));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return deferredResult;
    }

    @RequestMapping("/call")
    public void call() {
        ResponseEntity<DeferredResult> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/users/deferredResultUser", DeferredResult.class);
        log.info("http status code：{}", responseEntity.getStatusCode());
    }

    @PostMapping("/")
    public void saveUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
    }
}
