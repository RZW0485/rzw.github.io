package Design.dbutils.Service;

import Design.dbutils.JDBC.DBHelper;
import Design.dbutils.UDNI.PowerBank;
import Design.dbutils.UDNI.RentalRecord;
import Design.dbutils.UDNI.RentalRecord1;
import Design.dbutils.UDNI.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class Orderservice {
    // 从数据库中查询电源信息列表
    public ArrayList<PowerBank> getPowerBankList() {

        ArrayList<PowerBank> powerList = new ArrayList<>();
        Connection conn = null;
        String sql = "SELECT * FROM  design.power_bank";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConn();
            PreparedStatement stmt1 = conn.prepareStatement(sql);
            rs = stmt1.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String power = rs.getString("power");
                String status = rs.getString("status");
                String price = rs.getString("price");
                powerList.add(new PowerBank(id, power, status, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, stmt, rs);
        }
        return powerList;
    }

    // 从数据库中查询租赁记录列表
    public ArrayList<RentalRecord> getRentalRecordList(User user) {
        String id1 = user.getId();

        ArrayList<RentalRecord> rentalList = new ArrayList<>();
        String sql = "SELECT * FROM design.rental_record where userid = ? ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            ResultSet rs1 = preparedStatement.executeQuery();
            while (rs1.next()) {
                String id = rs1.getString("id");
                String powerId = rs1.getString("power_id");
                String createTime = rs1.getString("create_time");
                String returnTime = rs1.getString("return_time");
                String duration = rs1.getString("duration");
                String amount = rs1.getString("amount");
                String status = rs1.getString("status");

                rentalList.add(new RentalRecord(id, powerId, createTime, returnTime, duration, amount, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, stmt, rs);
        }


        return rentalList;


    }

    // 更新电源的状态
    public void updatePowerStatus(String powerId, String status) {
        String sql = "UPDATE design.power_bank SET status=? WHERE id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBHelper.getConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setString(2, powerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, pstmt, null);
        }


    }

    // 在数据库中创建租赁记录
    public void createRentalRecord(RentalRecord rentalRecord, User user) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBHelper.getConn();
            pstmt = conn.prepareStatement("INSERT INTO design.rental_record(id, power_id, create_time, return_time, duration, amount, status,userid) VALUES(?, ?, ?, ?, ?, ?, ?,?)");
            pstmt.setString(1, rentalRecord.getId());
            pstmt.setString(2, rentalRecord.getPowerId());
            pstmt.setString(3, rentalRecord.getCreateTime());
            pstmt.setString(4, rentalRecord.getReturnTime());
            pstmt.setString(5, rentalRecord.getDuration());
            pstmt.setString(6, rentalRecord.getAmount());
            pstmt.setString(7, rentalRecord.getStatus());
            pstmt.setString(8, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, pstmt, null);
        }


    }

    // 在数据库中更新租赁记录
    public void updateRentalRecord(String id, String returnTime, Double money, String time) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBHelper.getConn();
            pstmt = conn.prepareStatement("UPDATE design.rental_record SET return_time=?,duration=?,amount=?,status=? WHERE id=?");
            String status = "已完成";
            pstmt.setString(1, returnTime);
            pstmt.setString(2, time + "小时");
            pstmt.setDouble(3, money);
            pstmt.setString(4, "已完成");
            pstmt.setString(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, pstmt, null);
        }

    }
    //查询历史数据的方法，包装成一个类


    public String findRentalRecordList(User user) {
        String id1 = user.getId();
        String sql = "SELECT * FROM design.rental_record where userid = ? ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String createTime = "";
        try {
            conn = DBHelper.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            ResultSet rs1 = preparedStatement.executeQuery();
            while (rs1.next()) {
                createTime = rs1.getString("create_time");


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, stmt, rs);
        }


        return createTime;


    }

    //数据库中新增电源信息
    public void updatepower_back(String id, String power, String status, String price) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        conn = DBHelper.getConn();

        try {
            pstmt1 = conn.prepareStatement("select *from design.power_bank where id = ?");
            pstmt1.setString(1, id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (pstmt1 == null) {
            JOptionPane.showMessageDialog(null, "该电源已存在，请重新输入id");
        } else {

            double coast = Double.parseDouble(price);
            try {
                pstmt1.close();
                pstmt = conn.prepareStatement("insert design.power_bank(id, power, status, price) VALUES (?,?,?,?)");
                pstmt.setString(1, id);
                pstmt.setString(2, power);
                pstmt.setString(3, status);
                pstmt.setDouble(4, coast);
                pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"保存成功");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DBHelper.closeAll(conn, pstmt, null);
            }

        }
    }

    //数据库中更新电源信息
    public void updatepower(String id, String power, String status, String price) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        conn = DBHelper.getConn();
        double coast = Double.parseDouble(price);
            try {
                pstmt = conn.prepareStatement("update design.power_bank set power=?,status = ?, price = ?where id = ?");
                pstmt.setString(1, power);
                pstmt.setString(2, status);
                pstmt.setDouble(3, coast);
                pstmt.setString(4,id);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null,"保存成功");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DBHelper.closeAll(conn, pstmt, null);
            }

        }
        //删除电宝操作
    public void delete(String id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        conn = DBHelper.getConn();
        try {
            pstmt = conn.prepareStatement("delete from design.power_bank where id = ?");
            pstmt.setString(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JOptionPane.showMessageDialog(null,"删除成功！");
            DBHelper.closeAll(conn, pstmt, null);
        }
    }
    //从数据库中调用租赁中的电宝
    public ArrayList<PowerBank> getPowerBankList1() {

        ArrayList<PowerBank> powerList = new ArrayList<>();
        Connection conn = null;
        String sql = "SELECT * FROM  design.power_bank where status = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConn();
            PreparedStatement stmt1 = conn.prepareStatement(sql);
            stmt1.setString(1,"租赁中");
            rs = stmt1.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String power = rs.getString("power");
                String status = rs.getString("status");
                String price = rs.getString("price");
                powerList.add(new PowerBank(id, power, status, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, stmt, rs);
        }
        return powerList;
    }
    //数据库中查询所有租赁信息，管理员调用
    // 从数据库中查询租赁记录列表
    public ArrayList<RentalRecord1> getRentalRecordList1() {
        String userid = null;

        ArrayList<RentalRecord1> rentalList = new ArrayList<>();
        String sql = "SELECT * FROM design.rental_record  ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs1 = preparedStatement.executeQuery();
            while (rs1.next()) {
                String id = rs1.getString("id");
                String powerId = rs1.getString("power_id");
                String createTime = rs1.getString("create_time");
                String returnTime = rs1.getString("return_time");
                String duration = rs1.getString("duration");
                String amount = rs1.getString("amount");
                String status = rs1.getString("status");
                userid = rs1.getString("userid");

                rentalList.add(new RentalRecord1(id, powerId, createTime, returnTime, duration, amount, status,userid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, stmt, rs);
        }


        return rentalList;


    }
    }

