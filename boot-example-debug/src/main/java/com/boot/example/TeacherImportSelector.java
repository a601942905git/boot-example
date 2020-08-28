package com.boot.example;

import com.boot.example.entity.Teacher;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

/**
 * com.boot.example.TeacherImportSelector
 *
 * @author lipeng
 * @date 2020/7/2 3:41 PM
 */
public class TeacherImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(@Nullable AnnotationMetadata importingClassMetadata) {
        return new String[]{Teacher.class.getName()};
    }
}
