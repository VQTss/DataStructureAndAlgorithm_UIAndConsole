/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package undirected_graph_conosole;

/**
 *
 * @author thaiq
 */
public class GVertex {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public GVertex(int value) {
        this.value = value;
    }

    public String getLabel() {
        return value + "";
    }
}
