package graph.matrix;

/**
 * @since jdk 1.8
 */
public class IntegerSymmetricMatrix extends IntegerSquareMatrix {

    public enum TranAngularMatrixType {
        LOWER, UPPER
    }

    //压缩矩阵，使用下三角存储。
    private Integer[] compress;

    public IntegerSymmetricMatrix(Integer[][] triangularMatrix, TranAngularMatrixType traiangularMatrixType) {

        super(triangularMatrix.length);

        int n = triangularMatrix.length;

        compress = new Integer[n * (n + 1) / 2];


        //如果是上三角矩阵，先反转下来。
        if (traiangularMatrixType.equals(TranAngularMatrixType.UPPER)) {
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    triangularMatrix[j][i] = triangularMatrix[i][j];
                }
            }
        }

        //symmetricMatrix[i][j] == Array[i*(i+1)/2+j]
        for (int i = 0; i < triangularMatrix.length; i++) {
            for (int j = 0; j <= i; j++) {
                compress[i * (i + 1) / 2 + j] = triangularMatrix[i][j];
            }
        }
    }

    public IntegerSymmetricMatrix(Integer[][] triangularMatrix, TranAngularMatrixType traiangularMatrixType, Integer diagonal) {

        super(triangularMatrix.length);

        int n = triangularMatrix.length;

        compress = new Integer[n * (n + 1) / 2];


        //如果是上三角矩阵，先反转下来。
        if (traiangularMatrixType.equals(TranAngularMatrixType.UPPER)) {
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    triangularMatrix[j][i] = triangularMatrix[i][j];
                }
            }
        }

        //symmetricMatrix[i][j] == Array[i*(i+1)/2+j] ; 其中，对角线部分作额外填充。
        for (int i = 0; i < triangularMatrix.length; i++) {
            for (int j = 0; j <= i; j++) {

                if (j == i) {
                    compress[i * (i + 1) / 2 + j] = diagonal;
                    continue;
                }

                compress[i * (i + 1) / 2 + j] = triangularMatrix[i][j];
            }
        }
    }


    @Override
    public Integer[] getRow(int indexOfRow) {

        Integer[] t = new Integer[edge];
        for (int i = 0; i < edge; i++) {
            t[i] = get(indexOfRow, i);
        }
        return t;
    }

    @Override
    public int getColumns() {
        return edge;
    }

    @Override
    public int getRows() {
        return edge;
    }

    @Override
    public int getEdge() {
        return edge;
    }

    public Integer get(int row, int column) {
        if (column > row) return compress[column * (column + 1) / 2 + row];
        else return compress[row * (row + 1) / 2 + column];
    }

    public void set(int row, int column, Integer val) {
        if (column > row) compress[column * (column + 1) / 2 + row] = val;
        else compress[row * (row + 1) / 2 + column] = val;
    }
}
