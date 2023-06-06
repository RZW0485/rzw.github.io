package Design.dbutils.Service;

import Design.dbutils.UDNI.User;
import Design.dbutils.UserDao.UserDaoImp1;

public class UserService {


    //注册功能,传入输入的值，包装成user
    public User singIn(String userName,String userPasswd) {

        User user = new User();
        //创建用户对象
        UserDaoImp1 udi = new UserDaoImp1();
        //创建一个空用户
        udi.creatNewUser();
        //获取序列号,并包装成6为的id号
        int serialnum = udi.getSerial();
        String id = String.format("%06d", serialnum);
        //包装成user
        user.setId(id);
        udi.addUser(id, serialnum);
        user.setName(userName);
        user.setPassword(userPasswd);
        user.setBalance(0);
        udi.updataUser(user);
        return user;
    }
}