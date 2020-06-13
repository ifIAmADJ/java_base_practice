package graph;

import org.junit.Test;

public class GraphJunit {

    @Test
    public void TestDijsktra() {

        int MAX = 65535;

        int[][] temp = {
                {0,3,2000,7,MAX,3},
                {3,0,4,2,MAX,4},
                {MAX,4,0,5,4,5},
                {7,2,5,0,6,3},
                {MAX,MAX,4,6,0,5},
                {3,2,MAX,6,3,0},
        };

        //3 0 4 2 7 4
        int[] dijkstra = new Graph(temp).Dijkstra(1);

        for (int i: dijkstra) {
            System.out.print(i + " ");
        }

    }
}
