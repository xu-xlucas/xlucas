package xlucas.hbase;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by Xlucas on 2018/3/15.
 */
public class ReadPic {

    public static void main (String[] args){
        final String PATH="C:\\Users\\slx\\Desktop\\pic";
        File file = new File(PATH);
        File [] files = file.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            File file1 = files[i];
            file1.getName();
            System.out.println(file1);
            System.out.println(file1.getName());
        }

    }
}
