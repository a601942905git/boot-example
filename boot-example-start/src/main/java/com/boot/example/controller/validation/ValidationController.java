package com.boot.example.controller.validation;

import com.boot.example.core.entity.validation.ValidationEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * com.boot.example.controller.validation.ValidationController
 *
 * @author lipeng
 * @dateTime 2018/11/24 下午2:53
 */
@RequestMapping("/validations")
@Controller
public class ValidationController {

    @RequestMapping("/")
    public String index(@Valid @RequestBody ValidationEntity validationEntity,
                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "validation/validation_fail";
        }
        return "validation/validation_success";
    }
}
