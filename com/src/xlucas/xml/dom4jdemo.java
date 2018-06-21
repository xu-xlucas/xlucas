package xlucas.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * Created by Xlucas on 2018/4/30.
 */
public class dom4jdemo {
    public static void main(String[] args){

        try {
            //获取Document
            SAXReader sr= new SAXReader();
            Document dc=sr.read("com/src/xlucas/xml/DTD.xml");
            //获取根元素
            Element rootElement = dc.getRootElement();
            //获取根元素以及属性值
            System.out.println(rootElement.getName()+" version "+rootElement.attributeValue("version"));
            //获取所有子元素
            List<Element> elements = rootElement.elements();
            //遍历素有子元素
            for(Element element:elements){
                //获取元素名
                    String chilredelement=element.getName();
                    if("servlet".equals(chilredelement)){
                        //获取子标签内容 方式一
                        Element serveltname=element.element("servlet-name");
                        System.out.println(serveltname.getText());
                        //获取子标签内容 方式二
                        System.out.println(element.elementText("servlet-class"));

                    }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
