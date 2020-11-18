package com.example.starter;


import com.example.school.Klass;
import com.example.school.School;
import com.example.school.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * <p>TODO
 * </p>
 *
 * @author xingpeng
 * @date 2020/11/17 3:41 下午
 **/

@Configuration
@ConditionalOnClass(value = {School.class, Klass.class, Student.class})
@EnableConfigurationProperties(SchoolConfig.class)
@ConditionalOnProperty(prefix = "school.config", value = "enable", havingValue = "true")
public class AutoConfiguration {
    @Bean(name = "school")
    @ConditionalOnMissingBean(value = School.class)
    public School getSchoolInfo(SchoolConfig schoolConfig) {
        Student student = new Student();
        student.setId(schoolConfig.getStudentId());
        student.setName(schoolConfig.getStudentName());
        Klass klass = new Klass();
        klass.setStudents(Collections.singletonList(student));
        School school = new School();
        school.setKlasses(Collections.singletonList(klass));
        return school;
    }
}
