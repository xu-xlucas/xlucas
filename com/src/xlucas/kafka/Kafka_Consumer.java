package xlucas.kafka;
//依赖的API也变化
import net.sf.json.JSONObject;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import xlucas.hbase.ConnectHbase;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Xlucas on 2018/3/20.
 */
public class Kafka_Consumer {
        public static void main(String [] args){
            //这个是用来配置kafka的参数
            Properties props=new Properties();
            //这里不是配置zookeeper了，这个是配置bootstrap.servers
            props.put("bootstrap.servers","cdh3:9092,cdh4:9092");
            //这个配置组，之前我记得好像不配置可以，现在如果不配置那么不能运行
            props.put("group.id","test");
            //序列化
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            Consumer<String,String> consumer =new KafkaConsumer<String, String>(props);
            //配置topic
            ConnectHbase ch=new ConnectHbase();
            List<Put> puts = new LinkedList<Put>();
            consumer.subscribe(Arrays.asList("test"));
            while (true) {
                //这里是得到ConsumerRecords实例
                ConsumerRecords<String, String> records = consumer.poll(10);
                for (ConsumerRecord<String, String> record : records) {
                    //这个有点好，可以直接通过record.offset()得到offset的值
                    JSONObject json = JSONObject.fromObject(record.value());
                   System.out.println(json.getString("message").split(" ")[0]);
                   // puts.clear();
                   // puts.clear();
                   // Put put = new Put(Bytes.toBytes(json.getString("message").split(" ")[0]));
                   // put.addColumn("cf1".getBytes(), "value".getBytes(), json.getString("message").split(" ")[1].getBytes());
                    //puts.add(put);
                    //System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }
                // ConnectHbase.savadata("filebeat",puts);
            }
        }
}
