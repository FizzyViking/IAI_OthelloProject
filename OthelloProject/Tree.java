import java.util.ArrayList;
import java.io.*;


public class Tree {
    Node root;
    public Tree(GameState s)
    {
        root = new Node(s, null, 5);

        root.getvalue(true, s.getPlayerInTurn());
    }


    public Position BestMove()
    { 
        int[][] board = root.state.getBoard();

        Node best = root;
        for(Node n : root.branches)
        {
            if(best == root || n.value>=best.value) best = n;
        }

        System.out.println(best.position);
        return best.position;
    }
}
