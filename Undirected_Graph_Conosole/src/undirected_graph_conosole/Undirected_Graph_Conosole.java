package undirected_graph_conosole;

import java.io.File;


/**
 *
 * @author Thai Vo Quoc CE160568
 */
public class Undirected_Graph_Conosole {

    /**
     * @param args the command line arguments
     */
    Function f = new Function();

    public void readData() {
//        f.readMatrixDataFile(new File("dijkstra_2.mtx"));
          f.readListDataFile(new File("dijkstra_2_lst.lst"));
    }

    public void solve() {
        f.DijkstraFull(0, 5);
    }

    public void printReslut() {
        f.printFileOutputList(new File("ex01_output.txt"));
    }

    public static void main(String[] args) {
        Undirected_Graph_Conosole b = new Undirected_Graph_Conosole();
        b.readData();
        b.solve();
        b.printReslut();
    }

}
