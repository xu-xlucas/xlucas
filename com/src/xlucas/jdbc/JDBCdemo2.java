package xlucas.jdbc;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Xlucas on 2018/4/14.
 */
public class JDBCdemo2 {
    private static String username;
    private static String password;
    private static String driver;
    private static String jdbcurl;
    /*
    静态代码块,用静态代码块来加载类的时候赋值变量
     */
    static{
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        driver = bundle.getString("driver.name");
        jdbcurl = bundle.getString("jdbcurl");
        username = bundle.getString("username");
        password = bundle.getString("password");
    }
    /*
    创建数据库连接
     */
    public static Connection getConnection(){
        Connection conn=null;

        try {
            Class.forName(driver);
            conn= (Connection) DriverManager.getConnection(jdbcurl,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    /*
    释放资源
     */
    public static void Release(Connection conn, PreparedStatement pr, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(pr!=null){
            try {
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
