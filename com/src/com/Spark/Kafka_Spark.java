package com.Spark;

import com.clearspring.analytics.util.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;


/**
 * Created by Xlucas on 2018/3/22.
 */
public class Kafka_Spark {
    public static void main(String[] args){
        SparkConf conf=new SparkConf();
        conf.setAppName("sparkstreaming");
        conf.setMaster("local[2]");
        JavaStreamingContext jsc =new JavaStreamingContext(conf,new Duration(2000));
        int numthread =1;
        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        topicMap.put("test",numthread);
        JavaPairReceiverInputDStream<String,String> message=
                KafkaUtils.createStream(jsc,"cdh3:2181","test",topicMap);
               // KafkaUtils.createStream(jsc,"cdh3:2181","test",topicMap);
        JavaDStream<String> lines = message.map(new org.apache.spark.api.java.function.Function<Tuple2<String, String>, String>() {
            @Override
            public String call(Tuple2<String, String> Tuple2) throws Exception {
                System.out.println(Tuple2._2());
                return Tuple2._2();
            }
        });
    jsc.start();
    jsc.awaitTermination();
    }
}
