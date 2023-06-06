package Design.dbutils.Service;

import Design.dbutils.JDBC.DBHelper;
import Design.dbutils.UDNI.PowerBank;
import Design.dbutils.UDNI.RentalRecord;
import Design.dbutils.UDNI.User;

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
       String id1 =  user.getId();

        ArrayList<RentalRecord> rentalList = new ArrayList<>();
        String sql = "SELECT * FROM design.rental_record where userid = ? ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,id1);
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
    public void createRentalRecord(RentalRecord rentalRecord,User user) {

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
    public void updateRentalRecord(String id, String returnTime, DefaultTableModel personalRentalTableModel) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBHelper.getConn();
            pstmt = conn.prepareStatement("UPDATE design.rental_record SET return_time=?,duration=?,amount=?,status=? WHERE id=?");
            String duration = "3 小时";
            double amount = 4.5;
            String status = "已完成";
            pstmt.setString(1, returnTime);
            pstmt.setString(2, duration);
            pstmt.setString(3,  "89.0");
            pstmt.setString(4, "已完成");
            pstmt.setString(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHelper.closeAll(conn, pstmt, null);
        }

    }
}