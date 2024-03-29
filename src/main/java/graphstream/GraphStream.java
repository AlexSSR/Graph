package graphstream;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import viewer.samples.compute.FlexibleGraph;
import viewer.samples.compute.GraphBuilder;
import viewer.samples.compute.GraphMeticsCompute;
import viewer.samples.compute.GraphStreamGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import viewer.util.EnhancedOption;

import java.io.File;


public class GraphStream {

    public static void main(String[] args) {

        String userdir = System.getProperty("user.dir");
        String userdirName = new File(userdir).getPath() + "/src" + "/main" + "/resources/";

        GraphBuilder graphBuilder = new GraphBuilder();

        Graph graph1 = graphBuilder.read(userdirName + "rbm24.easy.graph").delimiter("\t").getGraph();
        Graph graph2 = graphBuilder.read(userdirName + "rbm24.media.graph").delimiter("\t").getGraph();
        Graph graph3 = graphBuilder.read(userdirName + "rbm24.high.graph").delimiter("\t").getGraph();
        Graph graph4 = graphBuilder.read(userdirName + "rbm24.hard.graph").delimiter("\t").getGraph();

        Iterable<Node> eachNode1 = (Iterable<Node>) graph1.getEachNode();
        Iterable<Node> eachNode2 = (Iterable<Node>) graph2.getEachNode();
        Iterable<Node> eachNode3 = (Iterable<Node>) graph3.getEachNode();
        Iterable<Node> eachNode4 = (Iterable<Node>) graph4.getEachNode();

        FlexibleGraph flexibleGraph1 = new GraphStreamGraph(graph1);
        FlexibleGraph flexibleGraph2 = new GraphStreamGraph(graph2);
        FlexibleGraph flexibleGraph3 = new GraphStreamGraph(graph3);
        FlexibleGraph flexibleGraph4 = new GraphStreamGraph(graph4);

        GraphMeticsCompute<Node> compute1 = new GraphMeticsCompute<>(eachNode1, flexibleGraph1);
        GraphMeticsCompute<Node> compute2 = new GraphMeticsCompute<>(eachNode2, flexibleGraph2);
        GraphMeticsCompute<Node> compute3 = new GraphMeticsCompute<>(eachNode3, flexibleGraph3);
        GraphMeticsCompute<Node> compute4 = new GraphMeticsCompute<>(eachNode4, flexibleGraph4);

        System.out.println(compute1.MAX_DD_ENTROPY);
        System.out.println(compute2.MAX_DD_ENTROPY);
        System.out.println(compute3.MAX_DD_ENTROPY);
        System.out.println(compute4.MAX_DD_ENTROPY);
        //graph.display();

        EnhancedOption option = new EnhancedOption();
        option.title().text("Entropy").subtext("各结构熵对比分析");
        option.tooltip().trigger(Trigger.axis);
        option.legend("SD熵");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data("简单", "中等", "复杂", "及其复杂"));
        option.yAxis(new ValueAxis());

        Bar bar1 = new Bar("classic");
        bar1.data(compute1.classicEntropyInfo(), compute2.classicEntropyInfo(), compute3.classicEntropyInfo(), compute4.classicEntropyInfo());
        //bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        //bar1.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        Bar bar2 = new Bar("DDEntropy");
        bar2.data(compute1.getDDEntropy(true), compute2.getDDEntropy(true), compute3.getDDEntropy(true), compute4.getDDEntropy(true));
        //bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        //bar2.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        Bar bar3 = new Bar("WUEntropy");
        bar3.data(compute1.getWUEntropy(), compute2.getWUEntropy(), compute3.getWUEntropy(), compute4.getWUEntropy());
        //bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        //bar3.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        Bar bar4 = new Bar("SDEntropy");
        bar4.data(compute1.getSDEntropy(), compute2.getSDEntropy(), compute3.getSDEntropy(), compute4.getSDEntropy());
        //bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        //bar4.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        option.series(bar1, bar2, bar3, bar4);
        option.exportToHtml("bar.html");
        option.view();
    }
}
