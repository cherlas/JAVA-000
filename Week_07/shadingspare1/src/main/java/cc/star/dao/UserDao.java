package cc.star.dao;


import cc.star.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
/*


    */
/**
     * 查询操作
     * @return
     *//*

    @ReadOnly
    @Override
    List<User> findAll();


    */
/**
     * 修改操作
     * @param testBean
     * @return
     *//*

    @UpdateOnly
    @Override
    User save(User testBean);
*/


}
