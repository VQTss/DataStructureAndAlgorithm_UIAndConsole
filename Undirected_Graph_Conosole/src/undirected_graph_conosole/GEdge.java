
package undirected_graph_conosole;

/**
 *
 * @author Thai Vo Quoc CE160568
 */
public class GEdge {
    private int value;
    private GVertex start;
    private GVertex end;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public GVertex getStart() {
        return start;
    }

    public void setStart(GVertex start) {
        this.start = start;
    }

    public GVertex getEnd() {
        return end;
    }

    public void setEnd(GVertex end) {
        this.end = end;
    }

    public GEdge(int value, GVertex start, GVertex end) {
        this.value = value;
        this.start = start;
        this.end = end;
    }
    
}
