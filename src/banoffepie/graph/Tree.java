package banoffepie.graph;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private final Node root;

    public Tree(){
        this.root = new Node();
    }

    public Node getRoot(){
        return this.root;
    }
}
