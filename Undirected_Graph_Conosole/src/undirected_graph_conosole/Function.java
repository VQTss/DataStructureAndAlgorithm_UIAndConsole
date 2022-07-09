package undirected_graph_conosole;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Thai Vo Quoc CE160568
 */
public class Function {

    private static final int MAX_VERTEX = 50;
    private static final String SEPARATOR = " ";

    private int graphType = 0;

    private String storeInfor = "";

    private int NumberOfVertices = 0;
    private int[][] graph;
    private ArrayList<GVertex> vertices;
    private ArrayList<GEdge> edges;
    private int startIndex = -1, stopIndex = -1;
    private int edgeValue = 1;

    private String result = ""; //result of BFS/DFS traversal
    boolean[] isVisted;
    Queue<Integer> q;
    Stack<Integer> s;

    int[] distance;
    int[] theVertexBefore; // beforeVertex -> currentVertex
    boolean isGraphOK;
    boolean isDrawPrimPath = false;
    public static final int Infinity = 1000000000;

    ArrayList<Integer> dijkstra_theVertexBefore[];
    ArrayList<String> dijkstraPath;
    String dijkstraMessage = "";
    int pathIndex = 0;
    String dijkstrPath = "";

    public Function() {
        distance = new int[MAX_VERTEX];
        theVertexBefore = new int[MAX_VERTEX];
        q = new LinkedList<>();
        s = new Stack<Integer>();
        isVisted = new boolean[MAX_VERTEX];
        traversalReset();

        this.graph = new int[MAX_VERTEX][MAX_VERTEX];
        for (int i = 0; i < MAX_VERTEX; i++) {
            for (int j = 0; j < MAX_VERTEX; j++) {
                this.graph[i][j] = 0;
            }
        }
        this.NumberOfVertices = 0;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();

    }

    private void traversalReset() {
        result = "";
        for (int i = 0; i < MAX_VERTEX; i++) {
            isVisted[i] = false;
        }
        q.clear();
        s.clear();
    }

    public int findVertexByValue(int v) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getValue() == v) {
                return i;
            }
        }
        return -1;
    }

    public int findEdgeByVertex(int from, int to) {
        GEdge edge;
        for (int i = 0; i < edges.size(); i++) {
            edge = edges.get(i);
            if ((edge.getStart().getValue() == from && edge.getEnd().getValue() == to)
                    || (edge.getStart().getValue() == to && edge.getEnd().getValue() == from)) {
                return i;
            }
        }
        return -1;
    }

    public int getNumberOfVertices() {
        return NumberOfVertices;
    }

    public int[][] getGraph() {
        return graph;
    }

    public ArrayList<GVertex> getVertices() {
        return vertices;
    }

    public void clear() {
        for (int i = 0; i < NumberOfVertices; i++) {
            for (int j = 0; j < NumberOfVertices; j++) {
                graph[i][j] = 0;
            }
        }
        this.vertices.clear();
        this.edges.clear();
        NumberOfVertices = 0;
        updateGraphInfo();

    }

    private void updateGraphInfo() {
        String giStr = "";
        if (this.graphType == 0) {          //MATRIX
            giStr += this.NumberOfVertices + "";
            for (int i = 0; i < this.NumberOfVertices; i++) {
                giStr += "\n" + graph[i][0];
                for (int j = 1; j < this.NumberOfVertices; j++) {
                    giStr += SEPARATOR + graph[i][j];
                }
            }

        } else {     //LIST
            int countEdge = 0;
            for (int i = 0; i < this.NumberOfVertices; i++) {
                for (int j = i + 1; j < this.NumberOfVertices; j++) {
                    if (graph[i][j] > 0) {
                        giStr += "\n" + vertices.get(i).getLabel() + " " + vertices.get(j).getLabel() + " " + graph[i][j];
                        ++countEdge;
                    }
                }
            }
            giStr = this.NumberOfVertices + " " + countEdge + giStr;
        }
        this.setStoreInfor(giStr);
    }

    public String getStoreInfor() {
        return storeInfor;
    }

    public void setStoreInfor(String storeInfor) {
        this.storeInfor = storeInfor;
    }

    public void addVertex() {
        this.vertices.add(new GVertex(this.NumberOfVertices++));

        for (int i = 0; i < this.NumberOfVertices; i++) {
            graph[i][this.NumberOfVertices - 1] = 0;
            graph[this.NumberOfVertices - 1][i] = 0;
        }
        updateGraphInfo();
    }

    public void addEdge(int startIndex, int stopIndex) {
        int selectedEdgeIndex = findEdgeByVertex(startIndex, stopIndex);
        if (selectedEdgeIndex == -1) {
            this.edges.add(new GEdge(edgeValue, this.vertices.get(startIndex), this.vertices.get(stopIndex)));
            graph[startIndex][stopIndex] = edgeValue;
            graph[stopIndex][startIndex] = edgeValue;
        }
        startIndex = stopIndex = -1;
        updateGraphInfo();
    }

    public void removeEdge(int from, int to) {
        int edgeIndex = findEdgeByVertex(from, to);
        if (edgeIndex > -1) {
            GEdge edge = this.edges.get(edgeIndex);
            String edgeLabel = edge.getStart().getLabel() + "-" + edge.getEnd().getLabel();
            this.removeEdge(edgeIndex);
        }
    }

    private void removeEdge(int edgeIndex) {
        GEdge edge = this.edges.get(edgeIndex);
        int from = edge.getStart().getValue();
        int to = edge.getEnd().getValue();
        graph[from][to] = 0;
        graph[to][from] = 0;
        this.edges.remove(edgeIndex);
        updateGraphInfo();
    }

    public void removeVertex(int vertex) {
        int edgeIndex = findVertexByValue(vertex);
        if (edgeIndex > -1) {
            this.removeVertex(edgeIndex);
        }
    }

    private void removeVertexByIndex(int edgeIndex) {
        for (int from = edgeIndex; from < this.NumberOfVertices - 1; from++) {

            for (int to = 0; to < this.NumberOfVertices; to++) {
                graph[from][to] = graph[from + 1][to];
                graph[to][from] = graph[to][from + 1];
            }
        }
        this.NumberOfVertices--;

        GEdge edge;
        for (int i = this.edges.size() - 1; i >= 0; i--) {
            edge = this.edges.get(i);
            if (edge.getStart().getValue() == edgeIndex || edge.getEnd().getValue() == edgeIndex) {
                this.edges.remove(i);
            }
        }

        this.vertices.remove(edgeIndex);
        for (int i = 0; i < this.NumberOfVertices; i++) {
            this.vertices.get(i).setValue(this.vertices.get(i).getValue() - 1);
        }
        updateGraphInfo();
    }

    void readMatrixDataFile(File openFile) {
        try (Scanner sc = new Scanner(openFile)) {
            this.edges.clear();
            this.vertices.clear();
            this.NumberOfVertices = sc.nextInt();

            for (int i = 0; i < this.NumberOfVertices; i++) {
                this.vertices.add(new GVertex(i));
            }

            for (int i = 0; i < this.NumberOfVertices; i++) {
                for (int j = 0; j < this.NumberOfVertices; j++) {
                    this.graph[i][j] = sc.nextInt();
                    if (i < j && this.graph[i][j] > 0) {
                        this.edges.add(new GEdge(this.graph[i][j], this.vertices.get(i), this.vertices.get(j)));
                    }
                }
            }
            dijkstraMessage = "";
            isDrawPrimPath = false;
            updateGraphInfo();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void readListDataFile(File openFile) {
        try (Scanner sc = new Scanner(openFile)) {
            this.edges.clear();
            this.vertices.clear();
            this.NumberOfVertices = sc.nextInt();
            int countEdge = sc.nextInt();

            for (int i = 0; i < this.NumberOfVertices; i++) {
                this.vertices.add(new GVertex(i));
            }

            for (int i = 0; i < this.NumberOfVertices; i++) {
                for (int j = 0; j < this.NumberOfVertices; j++) {
                    this.graph[i][j] = 0;

                }
            }

            int start, end, value;
            for (int i = 0; i < countEdge; i++) {
                start = sc.nextInt();
                end = sc.nextInt();
                value = sc.nextInt();
                this.edges.add(new GEdge(value, this.vertices.get(start), this.vertices.get(end)));
                this.graph[start][end] = this.graph[end][start] = value;
            }
            dijkstraMessage = "";
            isDrawPrimPath = false;
            updateGraphInfo();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int findNearestVertex() {
        int minIndex = - 1;
        int minValue = Infinity;
        for (int i = 0; i < NumberOfVertices; i++) {
            if (isVisted[i] == false && distance[i] < minValue) {
                minValue = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    void printFileOutputMatrix(File openFile) {
        try {
            ArrayList<GVertex> verties = getVertices();
            int[][] graph = getGraph();
            int numberOfVertex = getNumberOfVertices();
            String strData = "";
            FileWriter fw = new FileWriter("ex01_output.txt ");
            strData += numberOfVertex;
            for (int i = 0; i < numberOfVertex; i++) {
                strData += "\n" + graph[i][0];
                for (int j = 1; j < numberOfVertex; j++) {
                    strData += " " + graph[i][j];
                }
            }
            fw.write(strData);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void printFileOutputList(File openFile) {
        ArrayList<GVertex> verties = getVertices();
        int[][] graph = getGraph();
        int numberOfVertex = getNumberOfVertices();
        FileWriter fw = null;
        String strData = "";

        try {
            int countEdge = 0;
            for (int i = 0; i < numberOfVertex - 1; i++) {
                for (int j = i + 1; j < numberOfVertex; j++) {
                    if (graph[i][j] > 0) {
                        strData += "\n" + i + " " + j + " " + graph[i][j];
                        ++countEdge;
                    }
                }
            }
            strData = numberOfVertex + " " + countEdge + strData;
            fw = new FileWriter(openFile);
            fw.write(strData);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    public void DijkstraReset() {
        dijkstra_theVertexBefore = new ArrayList[MAX_VERTEX];

        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Infinity;
            dijkstra_theVertexBefore[i] = new ArrayList<>();
            dijkstra_theVertexBefore[i].add(i);
            isVisted[i] = false;
        }
        dijkstraPath = new ArrayList<>();
        dijkstraMessage = "";
        pathIndex = 0;
    }

    void Dijkstra(int startVertex, int endVertex) {

        DijkstraReset();
        distance[0] = 0;
        int currentVertex;
        isGraphOK = true;
        for (int i = 0; i < NumberOfVertices; i++) {
            currentVertex = findNearestVertex();
            if (currentVertex == -1) {
                // đồ thị không liên thông => không tìm thấy cây khung có trọng số nhỏ nhất
                isGraphOK = false;
                break;
            } else {
                isVisted[currentVertex] = true;
                for (int toVertext = 0; toVertext < NumberOfVertices; toVertext++) {
                    if (isVisted[toVertext] == false
                            && graph[currentVertex][toVertext] > 0
                            && distance[currentVertex] + graph[currentVertex][toVertext] < distance[toVertext]) {
                        distance[toVertext] = distance[currentVertex] + graph[currentVertex][toVertext];
                        theVertexBefore[toVertext] = currentVertex;
                    }
                }
            }
        }
        if (isGraphOK) {
            dijkstrPath = "" + endVertex;
            currentVertex = endVertex;
            while (currentVertex != startVertex) {
                currentVertex = theVertexBefore[currentVertex];
                dijkstrPath = currentVertex + "->" + dijkstrPath;
            }
            dijkstraMessage = "The length of the shorted path from " + startVertex + " to " + endVertex
                    + " is " + distance[endVertex] + ": " + dijkstrPath;
        } else {
            dijkstraMessage = "Cannot find path from " + startVertex + " to " + endVertex + "!";
        }
        System.out.println(dijkstraMessage);
        isDrawPrimPath = false;

    }

    public void DijkstraFull(int startVertex, int endVertex) {
        DijkstraReset();
        distance[startVertex] = 0;
        int currentVertex;
        isGraphOK = true;
        for (int i = 0; i < NumberOfVertices; i++) {
            currentVertex = findNearestVertex();
            if (currentVertex == -1) {
                // đồ thị ko liên thông => không tìm thấy cây khung có trọng số nhỏ nhất
                isGraphOK = false;
                break;
            } else {
                isVisted[currentVertex] = true;
                for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                    if ((isVisted[toVertex] == false || toVertex == endVertex)
                            && graph[currentVertex][toVertex] > 0
                            && distance[currentVertex] + graph[currentVertex][toVertex] <= distance[toVertex]) {
                        if (distance[currentVertex] + graph[currentVertex][toVertex] < distance[toVertex]) {
                            dijkstra_theVertexBefore[toVertex].clear();
                        }
                        distance[toVertex] = distance[currentVertex] + graph[currentVertex][toVertex];
                        dijkstra_theVertexBefore[toVertex].add(currentVertex);
                    }
                }
            }
        }
        if (isGraphOK) {
            dijkstraPath.clear();
            String path = "" + endVertex;
            currentVertex = endVertex;
            Dijkstra_displayPath(path, currentVertex, startVertex, endVertex);

            dijkstraMessage = "The length of the shortest path from " + startVertex + " to " + endVertex
                    + " is " + distance[endVertex] + ": ";
        } else {
            dijkstraMessage = "Can't find path from " + startVertex + " to " + endVertex + "!";
        }
        isDrawPrimPath = false;
        if (dijkstraPath.size() > 0) {
            for (int i = 0; i < dijkstraPath.size(); i++) {
                System.out.println("#" + (i + 1) + ". " + dijkstraPath.get(i));
            }
        }
    }

    public void Dijkstra_displayPath(String path, int currentVertex, int startVertex, int endVertex) {
        if (currentVertex != endVertex) {
            path = currentVertex + "->" + path;
        }
        if (currentVertex == startVertex) {
            dijkstraPath.add(path);
        } else {
            for (int i = 0; i < dijkstra_theVertexBefore[currentVertex].size(); i++) {
                Dijkstra_displayPath(path, dijkstra_theVertexBefore[currentVertex].get(i), startVertex, endVertex);
            }
        }
    }

    public void PrimReset() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Infinity;
            theVertexBefore[i] = i;
            isVisted[i] = false;
        }
        isDrawPrimPath = true;

    }

    public void Prim() {
        PrimReset();
        String str = "";
        distance[0] = 0;
        int currentVertex;
        isGraphOK = true;
        for (int i = 0; i < NumberOfVertices; i++) {
            currentVertex = findNearestVertex();
            if (currentVertex == -1) {
                // đồ thị không liên thông => không tìm thấy cây khung có trọng số nhỏ nhất
                isGraphOK = false;
                return;
            } else {
                isVisted[currentVertex] = true;
                for (int toVertext = 0; toVertext < NumberOfVertices; toVertext++) {
                    if (isVisted[toVertext] == false && graph[currentVertex][toVertext] > 0
                            && graph[currentVertex][toVertext] < distance[toVertext]) {
                        distance[toVertext] = graph[currentVertex][toVertext];
                        theVertexBefore[toVertext] = currentVertex;
                    }
                }
            }
        }
        String pathPrim = "";
        if (isGraphOK) {
            int sum = 0;
            for (int i = 0; i < NumberOfVertices; i++) {
                sum += distance[i];
            }
            str = "The minimum spanning tree has the min value is " + sum;
            System.out.println(str);

           
            
//            for (int i = 0; i < vertices.size(); i++) {
//                pathPrim += vertices.get(i).getValue();
//            }
//            System.out.println(pathPrim);
            

        } else {
            str = "The graph is not connected";
        }

    }

    public void BFS(int startVertex) {
        traversalReset();

        isVisted[startVertex] = true;
        q.add(startVertex);
        int fromVertex;
        result = "BFS traversal from the vertex " + startVertex + " is: ";
        while (!q.isEmpty()) {
            fromVertex = q.poll();
            result += fromVertex + ", ";
            for (int i = 0; i < NumberOfVertices; i++) {
//                System.out.println("from: " + fromVertex  + " to: " + i +" value: " + graph[fromVertex][i]);
                if (isVisted[i] == false && graph[fromVertex][i] > 0) {
                    q.add(i);
                    isVisted[i] = true;
                }
            }
        }

    }

    public void DFS(int startVertex) {
        traversalReset();
        s.push(startVertex);
        int fromVertex;
        String str = "";
        result = "DFS traversal from the vertex " + startVertex + " is: ";
        while (!s.isEmpty()) {
            fromVertex = s.pop();
            if (isVisted[fromVertex] == false) {
//                System.out.println("Str: " + str);
                result += fromVertex + ", ";
//                System.out.println("fromVertex: " + fromVertex);
//                System.out.println(result);
                isVisted[fromVertex] = true;
                for (int i = NumberOfVertices - 1; i >= 0; i--) {
//                    System.out.println("from: " + fromVertex + " to: " + i + " "
//                            + "select: " + isVisted[i]);
                    if (isVisted[i] == false && graph[fromVertex][i] > 0) {
                        s.push(i);
                        str += i + ", ";
                    }
                }
            }
        }
    }

    public String getResult() {
        return result;
    }

}
