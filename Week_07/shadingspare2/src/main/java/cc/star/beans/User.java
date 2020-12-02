package cc.star.beans;

import lombok.Data;

import javax.persistence.*;

@Table(name = "user")
@Data
public class User {

    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Integer id;

    @Column(name = "name")
    private String name;
}
