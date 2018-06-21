package xlucas.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Created by Xlucas on 2018/6/4.
 */
public class KafkaProducerDemo {
    public static void main(String[] args){
        //消息发送是异步还是同步发送
        boolean isAsync=args.length==0||!args[0].trim().equalsIgnoreCase("sync");
        Properties props=new Properties();
        //kafka服务端的主机名和端口号
        props.put("bootstrap.servers","slx-PC:9092");
        //kafka客户端的id
        props.put("client.id","kafkaproducer");
        //消息的key和value都是字节数组，为了将java对象转化为字节数组，可以配置key.serializer和value.serializer两个序列化，完成转化
        props.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //生产者的核心类
        KafkaProducer producer1 = new KafkaProducer<>(props);
        //向指定的topic发送消息
        String topic="test";
        int messageNo=1;//定义key
        while(true){
            String messageStr="Message_"+messageNo;//消息的value
            long startTime=System.currentTimeMillis();
            if(isAsync){//异步消息
                //第一个参数是ProducerRecord 类型的对象，封装了目标topic、消息的key和value
                //第二个参数是一个CallBack对象，当生产者接收到Kafka发来的ACK确认消息的时候，会调用此CallBack对象的onCompletion()方法,实现回调功能
                producer1.send(new ProducerRecord<>(topic,messageNo,messageStr),new ProducerCallBack(startTime,messageNo,messageStr));
            }else{//同步发送
                try {
                    //KafkaProducer.send()方法的返回值类型是Future<RecordMetadata>
                    //这里通过Future.get()方法，阻塞当前线程，等待kafka服务端的ACK响应
                    producer1.send(new ProducerRecord<>(topic,messageNo,messageStr)).get();
                    System.out.println("sent message "+messageNo+", "+messageStr);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            ++messageNo;//递增消息的key
        }

    }
}

class ProducerCallBack implements Callback{//回调函数
    private final long startTime;//开始发送消息的时间戳
    private final int key;//消息的key
    private  final String message;//消息的value
    public ProducerCallBack(long startTime,int key ,String message){
        this.startTime=startTime;
        this.key=key;
        this.message=message;
    }
    //生产者成功发送消息，收到kafka服务端发来的ACK确认消息后，会调用此回调函数，
    //recordMetadata 生产者发送的消息的元数据，  如果发送过程中出现了异常，此参数是null
    //e 发送过程中出现的异常，如果发送成功，则此参数为null
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        long elapsedTime=System.currentTimeMillis()-startTime;
        if(recordMetadata!=null) {
            //recordMetadata 这个中间包含了分区信息，offset等信息
            System.out.println(message + "send to partition " + recordMetadata.partition() + " offset " + recordMetadata.offset() + " " + elapsedTime);
        }else{
            e.printStackTrace();
        }


    }
}
