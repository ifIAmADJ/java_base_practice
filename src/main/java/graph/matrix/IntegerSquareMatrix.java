package graph.matrix;

public abstract class IntegerSquareMatrix extends IntegerMatrix{

    protected int edge;
    abstract public int getEdge();

    protected IntegerSquareMatrix(int edge) {
        this.edge = edge;
        this.column = edge;
        this.row =edge;
    }
}
