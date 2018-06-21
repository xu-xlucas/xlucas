package xlucas.xml;

/**
 * Created by Xlucas on 2018/4/30.
 */
public class spark implements bigdata{
    @Override
    public void init() {
        System.out.println("spark 启动中");
    }

    @Override
    public void service() {
        System.out.println("spark 服务中");
    }

    @Override
    public void destory() {
        System.out.println("spark 停止中");
    }
}
