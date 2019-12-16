package viewer.samples.compute;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class GraphStreamGraph implements FlexibleGraph<Node> {

    private Graph graph;

    public GraphStreamGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public int getDegree(Node vertex) {
        return vertex.getDegree();
    }

    @Override
    public int getVertexSize() {
        return graph.getNodeCount();
    }

    @Override
    public void display() {
        graph.display();
    }
}
