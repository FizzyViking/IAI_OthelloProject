import java.util.ArrayList;


public class Node{
	int value;
	GameState state;
	ArrayList<Node> branches;

	public node(GameState _state)
	{
		value = -1;
		state = _state;
		branches = new ArrayList<Node>();
	}
	public void generateBranches()
	{
		for(Position p : legalMoves())
		{
			GameState s = new GameState(state.getBoard, state.getPlayerInTurn);
			s.insertToken(p);
			branches.Add(new Node(s));
		}
	}

	public int getvalue(boolean isMax, int color)
	{
		if(value != -1)
		{
			return value;
		}

		if(branches.size == 0)
		{
			return state.countTokens()[color-1]
		}

		int val = 0;
		if(isMax)
		{
			val = 0;
		}else
		{
			val = state.size;
		}
		for(Node n : branches)
		{
			int tempval = n.getvalue(!isMax, color);
			if((tempval > val && isMax) || (tempval < val && !isMax))
			{
				val = tempval;
			}
		}
		value = tempval;
		return value;
	}
}
