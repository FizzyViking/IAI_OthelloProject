import java.util.ArrayList;


public class Tree {
    Node root;
    public Tree(GameState s)
    {
        root = new Node(s, null, 10);
        root.getvalue(true, s.getPlayerInTurn());
    }

    public void update(GameState s)
    {
        for(Node n : root.branches)
        {
            if(n.state.equals(s)) root = n;
        }
    }

    public Position BestMove()
    {
        Node best = null;
        for(Node n : root.branches)
        {
            if(best == null || n.value>best.value) best = n;
        }
        root = best;
        return best.position;
    }
}
