package minmax;

public class MinMax {
    /**
     * The move done by the opponent
     */
    private Node root;

    public static int DEPTH = 50;

    public MinMax() {
        this.root = new Node();
    }

    public void buildTree(BoardStatus boardStatus, int botID) {
        this.root.buildRoot(boardStatus, botID, DEPTH);
    }

    public Node minimax(int botID){
        return this.root.minimax(botID);
    }

    public void reset(){
        this.root = new Node();
    }

}
