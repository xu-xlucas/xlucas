package xlucas.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * Created by Xlucas on 2018/4/30.
 */
public class bigdataDemo2 {
    public static void main(String[] args){

        try {
            SAXReader sr=new SAXReader();
            Document read = sr.read("com/src/xlucas/xml/DTD.xml");
            Element rootElement = read.getRootElement();

            Element elements = rootElement.element("servlet");
            String servletclass=elements.elementText("servlet-class");
            Class clazz=Class.forName(servletclass);
            hadoop dp=(hadoop) clazz.newInstance();
            dp.init();
            dp.service();
            dp.destory();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
