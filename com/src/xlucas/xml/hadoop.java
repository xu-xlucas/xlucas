package xlucas.xml;

/**
 * Created by Xlucas on 2018/4/30.
 */
public class hadoop implements bigdata{
    @Override
    public void init() {
        System.out.println("hadoop 启动中");
    }

    @Override
    public void service() {
        System.out.println("hadoop 服务中");
    }

    @Override
    public void destory() {
        System.out.println("hadoop 停止中");
    }
}
