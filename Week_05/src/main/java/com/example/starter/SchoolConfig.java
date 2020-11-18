package com.example.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>TODO
 * </p>
 *
 * @author xingpeng
 * @date 2020/11/17 3:35 下午
 **/
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "school.config")
public class SchoolConfig {
    private Integer studentId;
    private String  StudentName;
}
