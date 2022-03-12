import java.util.ArrayList;

/**
 * A simple OthelloAI-implementation. The method to decide the next move just
 * returns the first legal move that it finds. 
 * @author Mai Ajspur
 * @version 9.2.2018
 */
public class NotAsDumAI implements IOthelloAI{

	/**
	 * Returns first legal move
	 */
	public Tree T;
	public Position decideMove(GameState s){
		T = new Tree(s);
		return T.BestMove();
	}
	
}
