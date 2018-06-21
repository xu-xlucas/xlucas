package xlucas.hbase;

/**
 * Created by Xlucas on 2018/3/15.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class ConnectHbase {
    public static Configuration conf;
    public static Connection connection;
    public static Admin admin;

    public static void main(String[] args) throws IOException {
        //createTable("filebeat", new String[]{"cf1", "cf2"});
        Put put = new Put(Bytes.toBytes("0001"));
        List<Put> puts = new LinkedList<Put>();
        put.addColumn("cf1".getBytes(), "name".getBytes(), "wangxiaoming".getBytes());
        put.addColumn("cf2".getBytes(), "age".getBytes(), "21".getBytes());
        puts.add(put);
        //savadata("filebeat",puts) ;
        Result result= getData("filebeat","0001");
        show(result);
    }

    //创建连接
    public static void init() {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.18.162,192.168.18.163,192.168.18.164");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("zookeeper.znode.parent", "/hbase");

        try {
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭连接
    public static void close() {
        try {
            if (null != admin)
                admin.close();
            if (null != connection)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void createTable(String tableNmae, String[] cols) throws IOException {
        init();
        TableName tableName = TableName.valueOf(tableNmae);
        if (admin.tableExists(tableName)) {
            System.out.println("talbe is exists!");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String col : cols) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
        }
        close();
    }

    public static void savadata(String tablename, List<Put> puts) {
        try {
            init();
            Table table = connection.getTable(TableName.valueOf(tablename));
            table.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
    }
    public static Result getData(String tablename,String rowkey){
        try {
            init();
            Table table = connection.getTable(TableName.valueOf(tablename));
            Get get=new Get(org.apache.hadoop.hbase.util.Bytes.toBytes(rowkey));
            return table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
        return null;
    }
    public static void show(Result result) {
        //得到rowkey的值
        String rowkey = org.apache.hadoop.hbase.util.Bytes.toString(result.getRow());

        KeyValue[] kvs = result.raw();
        for (KeyValue kv : kvs) {
            //得到列族
            String family = org.apache.hadoop.hbase.util.Bytes.toString(kv.getFamily());
            //得到列名
            String qualifier = org.apache.hadoop.hbase.util.Bytes.toString(kv.getQualifier());
            //得到列值
            String value = org.apache.hadoop.hbase.util.Bytes.toString(kv.getValue());
            System.out.println("key->" + rowkey + " family->" + family + " qualifier->" + qualifier + " value->" + value);
        }
    }
}
