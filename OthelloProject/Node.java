import java.util.ArrayList;


public class Node{
	int value;
	GameState state;
	Position position;
	ArrayList<Node> branches;

	public Node(GameState _state, Position pos, int depth)
	{
		value = -1;
		state = _state;
		branches = new ArrayList<Node>();
		position = pos;
		generateBranches(depth);

	}
	public void generateBranches(int depth)
	{
		if(depth<1 || branches.size() != 0) return; //if the recursion has reached the given depth or if the node already has branches, it will not generate any further
		for(Position p : state.legalMoves())
		{
			GameState s = new GameState(state.getBoard(), state.getPlayerInTurn());
			s.insertToken(p);
			Node n = new Node(s,p,depth-1);
			branches.add(n);
		}
	}

	public int getvalue(boolean isMax, int color)
	{
		if(value != -1)
		{
			return value;
		}

		if(branches.size() == 0)
		{
			return state.countTokens()[color-1];
		}

		int val = 0;
		if(isMax)
		{
			val = 0;
		}else
		{
			val = 42069360;
		}
		for(Node n : branches)
		{
			int tempval = n.getvalue(!isMax, color);
			if((tempval > val && isMax) || (tempval < val && !isMax))
			{
				val = tempval;
			}
		}
		value = val;
		return value;
	}
}
