package com.boot.example;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.UserController
 *
 * @author lipengi
 * @dateTime 2018/12/11 下午6:07
 */
@RestController
@RequestMapping("/validate/users")
public class UserController {

    @RequestMapping("/")
    public String index(@RequestBody @Valid User user, BindingResult bindingResult) {
        List<FieldError> list = bindingResult.getFieldErrors();
        List<String> messageList = new ArrayList<>();
        for (FieldError fieldError : list) {
            messageList.add(fieldError.getDefaultMessage());
        }
        return messageList.toString();
    }
}
