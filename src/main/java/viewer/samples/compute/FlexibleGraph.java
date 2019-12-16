package viewer.samples.compute;

//用于可插拔的图的模型构建
public interface FlexibleGraph<A> {

    int getDegree(A vertex);

    int getVertexSize();

    void display();
}
