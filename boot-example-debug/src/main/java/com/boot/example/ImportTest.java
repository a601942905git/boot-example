package com.boot.example;

import com.boot.example.entity.Student;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.ImportTest
 *
 * @author lipeng
 * @date 2020/7/2 3:37 PM
 */
@Component
@Import(value = {Student.class, TeacherImportSelector.class, AnimalImportBeanDefinitionRegistrar.class})
public class ImportTest {

}
