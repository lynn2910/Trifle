package banoffepie.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public static int ID_CURSOR = 0;

    public static void resetIdCursor(){
        ID_CURSOR = 0;
    }

    private final int id;
    private final List<Node> children;

    public Node() {
        this.id = ID_CURSOR++;
        this.children = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return "Node(" + this.getId() + ")";
    }

    public String formatToString() { return  formatToString(0); }

    public String formatToString(int indent) {
        indent++;
        String baseIdent = "  ".repeat(indent);
        StringBuilder sb = new StringBuilder();
        sb.append(this);
        sb.append(" [");
        if (children.isEmpty()){
            sb.append("]\n");
            return sb.toString();
        }
        sb.append("\n");

        for (Node child: getChildren()) {
            sb.append(baseIdent);
            sb.append(child.formatToString(indent + 1));
        }

        sb.append("  ".repeat(indent - 1 > 0 ? indent - 2 : 0));
        sb.append("]\n");

        return sb.toString();
    }
}
