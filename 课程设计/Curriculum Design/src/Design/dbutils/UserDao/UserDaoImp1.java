package Design.dbutils.UserDao;

import Design.dbutils.JDBC.DBHelper;
import Design.dbutils.UDNI.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImp1 implements UserDao{

    @Override
    public User findUserbyId(String id) {
        if(id == null){
            return  null;
        }
       Connection conn = DBHelper.getConn();
        String sql = "SELECT * FROM  design.user where id = ?;";
        PreparedStatement stat = null;
        ResultSet rs = null;
        User u = new User();
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1,id);
            rs = stat.executeQuery();
            while (rs.next()){
                u.setId(String.valueOf(rs.getString("id")));
                u.setName(rs.getString("name"));
                u.setPassword(rs.getString("password"));
                u.setBalance(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.closeAll(conn,stat,rs);
        }
if(u.getId().equals("")){
    return null;
}else {
    return u;
}
    }

    @Override

   public void creatNewUser() {
        Connection conn = DBHelper.getConn();
        String  sql  = "INSERT  design.user(id,name,password,balance) VALUE(null,null,null,null);";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(sql);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn,stat,rs);
        }
    }

    @Override
    public void addUser(String id, int serialNum) {
        Connection conn = DBHelper.getConn();
        String sql = "UPDATE design.user SET id=? WHERE serialnum =?;";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat =conn.prepareStatement(sql);
            stat.setString(1,id);
            stat.setInt(2,serialNum);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn,stat,rs);
        }

    }

    @Override
    public void updataUser(User user) {
       Connection coon = DBHelper.getConn();
        String sql = "UPDATE design.user SET name=?,password=?,balance=? WHERE id=?";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = coon.prepareStatement(sql);
            stat.setString(1,user.getName());
            stat.setString(2, user.getPassword());
            stat.setDouble(3,user.getBalance());
            stat.setString(4,user.getId());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(coon,stat,rs);
        }


    }

    @Override
    public int getSerial() {
        int serialNum = 0;
        Connection conn = DBHelper.getConn();
        String sql = "select * from design.user where id is null;";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();
            while (rs.next()){
                serialNum = rs.getInt("serialnum");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.closeAll(conn,stat,rs);
        }
        return serialNum;
    }
    public User findUserbyName(String name) {
        if(name == null){
            return  null;
        }
        Connection conn = DBHelper.getConn();
        String sql = "SELECT * FROM  design.user where name =?;";
        PreparedStatement stat = null;
        ResultSet rs = null;
        User u = new User();
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1,name);
            rs = stat.executeQuery();
            while (rs.next()){
                u.setId(rs.getString("id"));
                u.setName(rs.getString("name"));
                u.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.closeAll(conn,stat,rs);
        }
        if(u.getName().equals("")){
            return null;
        }else {
            return u;
        }
    }
}
