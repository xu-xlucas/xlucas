package xlucas.hive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;
import org.apache.hadoop.hive.ql.lib.DefaultGraphWalker;
import org.apache.hadoop.hive.ql.lib.DefaultRuleDispatcher;
import org.apache.hadoop.hive.ql.lib.Dispatcher;
import org.apache.hadoop.hive.ql.lib.GraphWalker;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.lib.NodeProcessor;
import org.apache.hadoop.hive.ql.lib.NodeProcessorCtx;
import org.apache.hadoop.hive.ql.lib.Rule;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.BaseSemanticAnalyzer;
import org.apache.hadoop.hive.ql.parse.ParseDriver;
import org.apache.hadoop.hive.ql.parse.ParseException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.parse.HiveParser;
public class LineageInfo implements NodeProcessor {
    TreeSet<String> inputTableList = new TreeSet();
    TreeSet<String> OutputTableList = new TreeSet();
    TreeSet<String> DropTableList = new TreeSet();
    TreeSet<String> CreateTableList = new TreeSet();
    public LineageInfo() {
    }

    public TreeSet<String> getInputTableList() {
        return this.inputTableList;
    }
    public TreeSet<String> getDropTableList() {
        return this.DropTableList;
    }
    public TreeSet<String> getCreateTableList() {
        return this.CreateTableList;
    }

    public TreeSet<String> getOutputTableList() {
        return this.OutputTableList;
    }

    public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx, Object... nodeOutputs) throws SemanticException {
        ASTNode pt = (ASTNode)nd;
        switch(pt.getToken().getType()) {
            case HiveParser.TOK_TAB:
                this.OutputTableList.add(BaseSemanticAnalyzer.getUnescapedName((ASTNode)pt.getChild(0)));
                break;
            case HiveParser.TOK_TABREF:
                ASTNode tabTree = (ASTNode)pt.getChild(0);
                String table_name = tabTree.getChildCount() == 1?BaseSemanticAnalyzer.getUnescapedName((ASTNode)tabTree.getChild(0)):BaseSemanticAnalyzer.getUnescapedName((ASTNode)tabTree.getChild(0)) + "." + tabTree.getChild(1);
                this.inputTableList.add(table_name);
                break;
            case HiveParser.TOK_DROPTABLE:
                this.DropTableList.add(BaseSemanticAnalyzer.getUnescapedName((ASTNode)pt.getChild(0)));
                break;
            case HiveParser.TOK_CREATETABLE:
                this.CreateTableList.add(BaseSemanticAnalyzer.getUnescapedName((ASTNode)pt.getChild(0)));
                break;
            case HiveParser.COMMENT:
                this.CreateTableList.add(BaseSemanticAnalyzer.getUnescapedName((ASTNode)pt.getChild(0)));
                break;
            case HiveParser.TOK_LOAD:
                this.CreateTableList.add(BaseSemanticAnalyzer.getUnescapedName((ASTNode)pt.getChild(0)));
                break;
        }

        return null;
    }

    public void getLineageInfo(String query) throws ParseException, SemanticException {
        ParseDriver pd = new ParseDriver();

        ASTNode tree;
        for(tree = pd.parse(query); tree.getToken() == null && tree.getChildCount() > 0; tree = (ASTNode)tree.getChild(0)) {
            ;
        }

        this.inputTableList.clear();
        this.OutputTableList.clear();
        Map<Rule, NodeProcessor> rules = new LinkedHashMap();
        Dispatcher disp = new DefaultRuleDispatcher(this, rules, (NodeProcessorCtx)null);
        GraphWalker ogw = new DefaultGraphWalker(disp);
        ArrayList<Node> topNodes = new ArrayList();
        topNodes.add(tree);
        ogw.startWalking(topNodes, (HashMap)null);
    }
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    public static void main(String[] args) throws IOException, ParseException, SemanticException {

        File file = new File("E:/hive.txt");
       // System.out.println(txt2String(file));

        String query=txt2String(file);
        query="--1111";
         query="INSERT OVERWRITE TABLE xlucas1 SELECT a.url FROM xlucas2 a join ods.xlucas3 b ON (a.url = b.domain)";
        LineageInfo lep = new LineageInfo();
        lep.getLineageInfo(query);
        Iterator i$ = lep.getInputTableList().iterator();

        String tab;
        while(i$.hasNext()) {
            tab = (String)i$.next();
            System.out.println("InputTable=" + tab);
        }

        i$ = lep.getOutputTableList().iterator();

        while(i$.hasNext()) {
            tab = (String)i$.next();
            System.out.println("OutputTable=" + tab);
        }

        i$ = lep.getDropTableList().iterator();

        while(i$.hasNext()) {
            tab = (String)i$.next();
            System.out.println("DropTable=" + tab);
        }

        i$ = lep.getCreateTableList().iterator();

        while(i$.hasNext()) {
            tab = (String)i$.next();
            System.out.println("CreateTable=" + tab);
        }

    }
}
