#学习笔记
## 方法一
````
@Component
public class Method1 {}

@AutoWired
private Method1 method1;
````
## 方法二
```
public class Method2 {
    @Bean
    public Method2() {}
}
```
## 方法三
```
<beans>
    <bean class="com.exapmle.Method3" id="method3" ></bean>
</beans>
```