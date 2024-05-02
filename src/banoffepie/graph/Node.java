package banoffepie.graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public static int ID_CURSOR = 0;

    private final int id;
    private final List<Node> childrens;

    public Node() {
        this.id = ID_CURSOR++;
        this.childrens = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Node> getChildren() {
        return childrens;
    }
}
