import org.junit.Test;
import graph.matrix.IntegerSymmetricMatrix;
import graph.matrix.IntegerSymmetricMatrix.*;

import java.util.Arrays;

/**
 * 克鲁斯卡尔的Java代码实现
 * => https://blog.csdn.net/weixin_38361153/article/details/88243035
 * <p>
 * C++语言实现克鲁斯卡尔算法
 * => https://blog.csdn.net/baidu_41774120/article/details/83794139
 */
public class KruskalTest {

    private static class Graph {

        //无向图的邻接矩阵是对称矩阵。
        IntegerSymmetricMatrix weight;
        Edge[] SortedEdges;

        public Graph(IntegerSymmetricMatrix weight, Edge[] edges) {
            this.weight = weight;
            this.SortedEdges = edges;
        }
    }

    private static class Edge implements Comparable<Edge> {
        //这里的顶点均使用数字标记。
        int start;
        int end;
        int weight;

        public Edge() {
        }

        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "start=" + start +
                    ", end=" + end +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }


    public static void Kruskal(Graph graph) {

        Edge[] sortedEdges = graph.SortedEdges;

        /*
         为了判断连通分量，这里使用一个parent来解决。
         在最开始，默认每个点都在单独的一个连通分量当中。
         其中,parent[u] = v ; 表示u号顶点的根为v。
         具体的判断参见findParent方法。
         因为之前规定了(i,j) , i < j。因此不可能有边是以0作为节点。
        */
        int[] parent = new int[graph.weight.getEdge()];

        /*
         * 虽然这个循环会将所有的边都会遍历一遍，但是根据最小生成树的性质，
         * 当我们找到的边数  =  顶点数 - 1 时，这个程序就可以退出了。
         *
         * */
        int hit = 0;

        for (int i = 0; i < sortedEdges.length; i++) {

            if (hit == graph.weight.getEdge() - 1) {
                break;
            }

            Edge e = sortedEdges[i];

            //检查边的起点的根（判断起点所在的连通分量）
            int startRoot = findParent(e.start, parent);

            //检查边的终点的根（判断终点所在的连通分量）
            int endRoot = findParent(e.end, parent);

            //为了避免环路，只有满足此条件才可以加入到边中。
            if (startRoot != endRoot) {
                System.out.println(String.format("(%d , %d ) -> %d", e.start, e.end, e.weight));
                parent[startRoot] = endRoot;
                hit++;
            }
        }
    }

    private static int findParent(int u, int[] parent) {

        while (parent[u] > 0) {
            u = parent[u];
        }

        return u;
    }


    @Test
    public void test() {

        /*
         设置一个n * n矩阵做邻接矩阵，记录点到点的边的权值。
         表示方法为：arr[i][j] = value => 从i到j的边，权值为value。
         在无向图当中，临界矩阵可以使用三角矩阵压缩。
         在这里定义规范： i  <  j，因此使用上三角矩阵压缩。
         */
        Integer[][] arr = new Integer[7][7];
        for (Integer[] line : arr) Arrays.fill(line, Integer.MAX_VALUE);

        //利用刚才的矩阵设置11条边的权值，只标记(i,j)，且i < j的边。
        arr[0][1] = 7;
        arr[0][3] = 5;
        arr[1][2] = 8;
        arr[1][3] = 9;
        arr[1][4] = 7;
        arr[2][4] = 5;
        arr[3][4] = 15;
        arr[3][5] = 6;
        arr[4][5] = 8;
        arr[4][6] = 9;
        arr[5][6] = 11;

        IntegerSymmetricMatrix weight = new IntegerSymmetricMatrix(arr, TranAngularMatrixType.UPPER, 0);

        //记录此11条边。
        Edge[] edges = new Edge[11];


        int k = 0;

        /*
          边使用(i,j)表示，其中i表示起点，j表示终点。
          并且有一个额外条件：i < j。（不存在i == j，因为没有环。）
          若存在(x,y)，且x > y的情况，则转而使用(y,x)去表示这条边。
         */
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 7; j++) {

                if (weight.get(i, j) < Integer.MAX_VALUE) {

                    edges[k] = new Edge(i, j, weight.get(i, j));

                    k++;
                }

            }
        }


        /*
           提前对这个边进行选取，简化克鲁斯卡尔算法的判断。
           程序每次只需要按顺序判断会不会引发回路即可。
         */
        Arrays.sort(edges);

        Kruskal(new Graph(weight, edges));
    }
}
