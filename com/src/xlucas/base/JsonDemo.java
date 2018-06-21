package xlucas.base;


import net.sf.json.JSONObject;

/**
 * Created by Xlucas on 2018/3/22.
 */
public class JsonDemo {
    public static void main(String[] args) {

        String jsonString ="{\"@timestamp\":\"2018-03-22T14:57:03.259Z\",\"beat\":{\"hostname\":\"cdh2\",\"name\":\"cdh2\",\"version\":\"5.6.8\"},\"input_type\":\"log\",\"message\":\"0494840611521730623 e9e473538a\",\"offset\":21886,\"source\":\"/opt/log/filebeat/a.log\",\"type\":\"log\"}";
        JSONObject json = JSONObject.fromObject(jsonString);
        System.out.println(json.getString("message"));
    }
}
