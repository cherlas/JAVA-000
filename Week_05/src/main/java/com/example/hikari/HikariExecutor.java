package com.example.hikari;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HikariExecutor implements CommandLineRunner {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public void run(String... args) throws Exception {
        String sql = "select * from user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>());
        users.forEach(System.out::println);
    }
}
