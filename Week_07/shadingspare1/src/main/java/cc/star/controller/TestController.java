package cc.star.controller;

import cc.star.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    public static final String SUCCESSMSG="操作成功";

    public static final String ERRORMSG="操作失败";


    @Autowired
    private TestService testService;

    @GetMapping("/getList")
    public List getList(){
        return testService.getList();
    }


    @GetMapping("/update")
    public String update(Integer id){
        try {
            testService.XXXXXXXXX(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改数据库失败");
            return ERRORMSG;
        }
        return SUCCESSMSG;
    }
}
