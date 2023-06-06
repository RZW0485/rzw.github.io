package Design.dbutils.UserDao;
import Design.dbutils.UDNI.User;

public interface UserDao {
    public User findUserbyId(String id);
    public void creatNewUser();
    public void addUser(String id,int serialNum);
    public void updataUser(User user);
    public int getSerial();

}
