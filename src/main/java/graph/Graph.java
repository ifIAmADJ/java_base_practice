package graph;


public class Graph{

    private int[][] weight;
    
    private int vNums;


    /**
     * Dijkstra : 用于求单源到其它所有顶点的最短路径。 
     *  1. 适用于有向图，无向图。
     *  2. 权值必须大于0。
     * 
     * 参考资料 : 
     *  1. CSDN论坛 <a herf ="https://blog.csdn.net/wangshuang1631/article/details/52637371">CSDN-Dijsktra Java实现版本</>
     *  2. 视频教程可以参考 <a href ="https://www.bilibili.com/video/BV1ct411q7jG/?spm_id_from=333.788.videocard.2">bili-算法演示部分</a>
     */
    public int[] Dijkstra(int startPos) {
        
        //一共有graph.length个顶点。true表示该结点已经走过，不再考虑。
        boolean[] visited = new boolean[vNums];

        //初始点到其它点的距离为最开始的邻接矩阵。
        int[] shortestPath  = weight[startPos];

        //-------用于表示最短路径，默认情况下全部是1。可以用其它的方式实现。------------//
        int[] last = new int[vNums];
        for (int i= 0; i < vNums;i++) last[i] = startPos;
        //------------------------------------------------------------------//

        //起点标记为：true。
        visited[startPos] = true;

        //走遍除了自身的所有点。
        for (int i = 1; i < vNums; i++) {

            //初始化最优点
            int k = -1;
            //初始化最优距离;
            long shortest = Long.MAX_VALUE;

            //找本轮的落脚点.
            for (int j = 0; j < vNums ; j++) {
                if(weight[startPos][j] < shortest  && !visited[j]){
                    k = j;
                    shortest = (long)weight[startPos][j];
                }
            }

            //找到落脚点之后，visit标记为true。
            visited[k] = true;
            shortestPath[k] = (int)shortest;

            //更新距离
            for (int j =0 ;j < vNums ; j++) {
                if(weight[startPos][k] + weight[k][j] < weight[startPos][j]  && !visited[j]){
                    weight[startPos][j] = weight[startPos][k] + weight[k][j];

                    //用于表示最短路径。
                    last[j] = k;
                }
            }
        }

        //非关键代码：该段用于打印在最短路径中，每个点的前驱顶点。应该是1 1 1 1 5 1
        for(int i = 0; i< vNums;i ++) System.out.println(i + "的前驱节点为:" + last[i]);

        return shortestPath;
    }

    public int[][] getWeight() {
        return weight;
    }

    public void setWeight(int[][] weight) {
        this.weight = weight;
    }

    public Graph(int[][] weight) {
        this.weight = weight;
        vNums =weight.length;
    }
}
