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

	@Override
	public Position decideMove(GameState s){
		// T = new Tree(s);
		// return T.BestMove();

		boolean isMax;
		if (s.getPlayerInTurn() == 1) {
			isMax = true;
		} else {
			isMax = false;
		}

		Pos max = MaxValue(s, Integer.MIN_VALUE, Integer.MAX_VALUE, isMax);
		return max.getMove();
	}


	public Pos MaxValue (GameState state, int _alpha, int _beta, boolean isMax) {

		if (state.legalMoves().size() == 0) {
			// Terminate
			return Eval(isMax, state);
		}

		int alpha = _alpha;
		int beta = _beta;
		int value = Integer.MIN_VALUE;
		Position newMove = null;

		// Go through all the legal moves
		for (Position p : state.legalMoves()) {

			GameState s = new GameState(state.getBoard(), state.getPlayerInTurn());
			s.insertToken(p);

			Pos next = MinValue(s, alpha, beta, !isMax);

			if (next.getUtilValue() > value) {
				value = next.getUtilValue();
				alpha = Math.min(alpha, value);
				newMove = p;
			}
			if (value <= beta) {
				// Cut off
				return new Pos(value, newMove);
			}
		}
		return new Pos(value, newMove);
	}

	public Pos MinValue (GameState state, int _alpha, int _beta, boolean isMax) {

		if (state.legalMoves().size() == 0) {
			// Terminate
			return Eval(isMax, state);
		}

		int alpha = _alpha;
		int beta = _beta;
		int value = Integer.MAX_VALUE;
		Position newMove = null;

		for (Position p : state.legalMoves()) {

			GameState s = new GameState(state.getBoard(), state.getPlayerInTurn());
			s.insertToken(p);

			Pos next = MaxValue(s, alpha, beta, !isMax);

			if (next.getUtilValue() < value) {
				value = next.getUtilValue();
				beta = Math.min(beta, value);
				newMove = p;
			}
			if (value <= alpha) {
				// Cut off
				return new Pos(value, newMove);
			}
		}
		return new Pos(value, newMove);
	}

	private Pos Eval (boolean isMax, GameState state) {
		int[] score = state.countTokens();
		int status;

		// Difference in white and black tokens used as evaluation
		if (isMax) {
			status = score[0] - score[1];
		} else {
			status = score[1] - score[0];
		}
		return new Pos(status, null);
	}

	class Pos {
		private int utilValue;
		private Position move;

		public Pos (int util, Position _move) {
			this.utilValue = util;
			this.move = _move;
		}

		public int getUtilValue() {
			return utilValue;
		}

		public Position getMove() {
			return move;
		}
	}
}