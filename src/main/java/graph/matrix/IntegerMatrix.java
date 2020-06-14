package graph.matrix;

public abstract class IntegerMatrix implements Cloneable {

    public abstract Integer get(int row,int column);
    public abstract int getColumns();
    public abstract int getRows();
    public abstract Integer[] getRow(int index);
//    public abstract int getColumn(int index);


    public abstract void set(int row, int column, Integer val);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected int column;
    protected int row;
}
