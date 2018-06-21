package xlucas.jdbc;



import org.junit.Test;

import java.sql.*;

/**
 * Created by Xlucas on 2018/4/1.
 */
public class JdbcConnect {
    public static void main(String[] args){
    }
    @Test
    public void testLogin() {
        try {
            login("root' or 'root", "*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9");
            login1("root' or 'root", "*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void login(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn= DriverManager.getConnection("jdbc:mysql://cdh1:3306/mysql","root","123456");
        String sql="select * from user where "+"User='"+username+"'"+" and Password='"+password+"'";
        Statement stat=conn.createStatement();
        ResultSet rs= stat.executeQuery(sql);
        if (rs.next()) {
            System.out.println("恭喜您，" + username + ",登录成功!");
        } else {
            System.out.println("账号或密码错误!");
        }
        if (rs != null)
            rs.close();
        if (stat != null)
            stat.close();
        if (conn != null)
            conn.close();

    }
    public void login1(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn= DriverManager.getConnection("jdbc:mysql://cdh1:3306/mysql","root","123456");
        String sql="select * from user where User=? and Password= ? ";
        PreparedStatement pstat= conn.prepareStatement(sql);
        pstat.setString(1,username);
        pstat.setString(2,password);
        ResultSet rs= pstat.executeQuery();
        if (rs.next()) {
            System.out.println("恭喜您，" + username + ",登录成功!");
        } else {
            System.out.println("账号或密码错误!");
        }
        if (rs != null)
            rs.close();
        if (pstat != null)
            pstat.close();
        if (conn != null)
            conn.close();

    }
}
