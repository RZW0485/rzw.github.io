package Design.dbutils.JDBC;

import java.sql.*;

public class DBHelper {
    private static Connection conn = null;
    private  static final String URL="jdbc:mysql://localhost:3306/ssobs?useSSL=false&serverTimezone=UTC";
    private static final String DRIVER ="com.mysql.cj.jdbc.Driver";
    private static final String DBUser = "root";
    private static final String DBPassword = "123456";

    public static Connection getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL,DBUser,DBPassword);
        } catch (SQLException e) {
            System.out.println("qud");
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            System.out.println("dier");
            throw new RuntimeException(e);

        }

        return conn;
    }

    public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if(rs != null){
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(pstmt != null){
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
