package xlucas.kafka;
//依赖的API也变化
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * Created by Xlucas on 2018/3/20.
 */
public class Kafka_Producer {
    public static void main(String[] args){
        //这个是用来配置kafka的参数
        Properties prop=new Properties();
        //这里不是配置broker.id了，这个是配置bootstrap.servers
        prop.put("bootstrap.servers","cdh2:9092,cdh3:9092");
        //下面是分别配置 key和value的序列化
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //这个地方和1.0X之前的版本有不一样的，这个是使用kafkaproducer 类来实例化
        Producer<String, String> producer=new KafkaProducer<>(prop);
        for (int i = 0; i < 100; i++) {
            //这个send 没有什么变化
            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));

        }
            producer.close();
    }
}
