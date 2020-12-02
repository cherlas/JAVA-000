package cc.star.service;

import cc.star.beans.User;
import cc.star.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TestService {


    @Autowired(required = false)
    private UserDao userDao;



    public List<User> getList(){
        return userDao.findAll();
    }


    public void update(Integer id){
        User user = new User();
        user.setId(id);
        user.setName("UPDATE NAME");
        userDao.save(user);
    }
}
