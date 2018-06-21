package xlucas.hive;

/**
 * Created by Xlucas on 2018/6/13.
 */
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.ql.parse.*;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.tools.LineageInfo;
import java.io.IOException;

public class HiveParse {
    public static void main(String[] args) throws Exception {

        String command = "INSERT OVERWRITE TABLE liuxiaowen.lxw3 SELECT a.url FROM liuxiaowen.lxw1 a join liuxiaowen.lxw2 b ON (a.url = b.domain)";
        ParseDriver pd = new ParseDriver();
        ASTNode tree = pd.parse(command);
        System.out.println(tree.getToken());
        System.out.println(tree.getChild(0));
        System.out.println(tree.dump());
    }
}
