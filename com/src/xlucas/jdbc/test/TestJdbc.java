package xlucas.jdbc.test;

import com.mysql.jdbc.Connection;
import org.junit.Test;
import xlucas.jdbc.JDBCDemo3;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Xlucas on 2018/4/15.
 */
public class TestJdbc {
    @Test
    public void TestFindUsername(){
    Connection conn=null;
        PreparedStatement pr=null;
        ResultSet rs=null;
        try {
            //创建连接
            conn= JDBCDemo3.getConnection();
            String sql="select * from user where User=? and Password= ?";
            //创建预执行实例
            pr=conn.prepareStatement(sql);
            //给sqll中的变量赋值
            pr.setString(1,"root");
            pr.setString(2,"*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9");
            //执行sql语句
            rs=pr.executeQuery();
            //打印返回的结果
            while(rs.next()){
                System.out.println(rs.getString(1)+"------------"+rs.getString(2));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //  释放资源
            JDBCDemo3.Release(conn,pr,rs);
        }

    }
}
