package cc.star.service;

import cc.star.annotation.ReadOnly;
import cc.star.annotation.UpdateOnly;
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



    @ReadOnly
    public List<User> getList(){
        return userDao.findAll();
    }


    @UpdateOnly
    public void update(Integer id){
        User user = new User();
        user.setId(id);
        user.setName("UPDATE NAME");
        userDao.save(user);
    }
}
