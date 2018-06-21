package com.Spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.GraphLoader;

/**
 * Created by Xlucas on 2018/4/17.
 */
public class SparkGraphXDemo1 {
    public static void main(String[] args){
        SparkConf conf= new SparkConf();
        conf.setMaster("lcoal[2]");
        conf.setAppName("SparkGraphXDemo1");
        JavaSparkContext jsc=new JavaSparkContext(conf);
      //  Graph<Integer, Integer> graph = GraphLoader.edgeListFile(jsc, "/aaa");
       // objectObjectGraph.
    }
}
