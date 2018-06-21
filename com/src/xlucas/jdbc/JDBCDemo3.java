package xlucas.jdbc;

import com.mysql.jdbc.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Xlucas on 2018/4/15.
 */
public class JDBCDemo3 {
    private static String username;
    private static String password;
    private static String driver;
    private static String jdbcurl;
    /*
    静态代码块,用静态代码块来加载类的时候赋值变量
     */
    static{
        try {
            // 1.通过当前类获取类加载器
        ClassLoader classLoader=JDBCDemo3.class.getClassLoader();
            // 2.通过类加载器的方法获得一个输入流
        InputStream is=classLoader.getResourceAsStream("DB.properties");
            // 3.创建一个properties对象
        Properties bundle=new Properties();
            // 4.加载输入流
            bundle.load(is);
            // 5.获取相关参数的值
            driver = bundle.getProperty("driver.name");
            jdbcurl = bundle.getProperty("jdbcurl");
            username = bundle.getProperty("username");
            password = bundle.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
