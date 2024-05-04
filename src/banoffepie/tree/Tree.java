package banoffepie.tree;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private final List<Node> nodes;

    public Tree(){
        this.nodes = new ArrayList<>();
    }

    public List<Node> getRoot(){
        return this.nodes;
    }
}
