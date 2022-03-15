import java.util.ArrayList;

/**
 * A minmax implementation of an Othello AI The method to decide the next move
 * returns the best move determined by the minmax algorithm
 * and terminates if there are no legal moves left
 * or if the depth limit has been reached
 */
public class NotAsDumAI implements IOthelloAI{

    /**
     * Returns the best legal move determined by the minmax algorithm
     */
    public Position decideMove(GameState s){
        System.out.println("NotAsDumbAI choosing");

        // start and endTime calculates the time it takes for the algorithm to return the best move
        var startTime = System.nanoTime();
        var pos =  MaxValue(s, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, s.getPlayerInTurn()).getPos();
        var endTime = System.nanoTime();
        System.out.println("NotAsDumbAI chooses: " + pos.toString());
        System.out.println("It took " + ((endTime-startTime)/1_000_000) + " seconds");

        return pos;
    }

    /**
     * Evaluates the state by calculating the number AIs tokens
     * subtracted from the number of opponets tokens.
     */
    public int CalculateScore(GameState state, int playerID){
        if (playerID == 1){
            return state.countTokens()[0] - state.countTokens()[1];
        }else {
            return state.countTokens()[1] - state.countTokens()[0];
        }
    }

    /**
     * Returns the best Pos (move and utility) for Max aka the AI
     */
    public Pos MaxValue(GameState state, int alpha, int beta, int depth, int playerID){

        //If no legal moves are available or if the depth limit has been reached terminate
        var legalMoves = state.legalMoves();
        if (legalMoves.isEmpty() || depth < 1 ){
            return new Pos(CalculateScore(state, playerID), null);
        }

        Position nextMove = null;
        int value = Integer.MIN_VALUE;

        // For each legal move at the current gamestate
        for (Position a : legalMoves) {

            // Make a new gamestate with the move a applied
            GameState _state = new GameState(state.getBoard(), state.getPlayerInTurn());
            _state.insertToken(a);

            // Get the next move and util for Min
            Pos next = MinValue(_state, alpha, beta, depth-1, playerID);

            // If the utility for Min's move is higher than our current value
            // Set our value to Min's move utility and set alpha to the max between alpha and the value
            if (next.getUtil() > value) {
                value = next.getUtil();
                alpha = Math.max(alpha, value);
                nextMove = a;
            }
            // If the value is higher or equal to beta, cut off here and return the move
            if (value >= beta) {
                return new Pos(value, nextMove);
            }
        }
        return new Pos(value, nextMove);
    }

    /**
     * Returns the best Pos (move and utility) for Min aka the opponent
     */
    public Pos MinValue(GameState state, int alpha, int beta, int depth, int playerID) {
        var legalMoves = state.legalMoves();
        if (legalMoves.isEmpty() || depth < 1 ){
            return new Pos(CalculateScore(state, playerID), null);
        }

        Position nextMove = null;
        int value = Integer.MAX_VALUE;

        for (Position a : state.legalMoves()) {

            GameState sim = new GameState(state.getBoard(), state.getPlayerInTurn());
            sim.insertToken(a);

            Pos next = MaxValue(sim, alpha, beta, depth-1, playerID);

            if (next.getUtil() < value) {
                value = next.getUtil();
                beta = Math.min(beta, value);
                nextMove = a;
            }
            if (value <= alpha) {
                return new Pos(value, nextMove);
            }
        }
        return new Pos(value, nextMove);
    }

    /**
     * Class representing a move + the utility for that move
     */
    static class Pos {
        int util;
        Position pos;

        public Pos (int _util, Position _pos) {
            this.util = _util;
            this.pos = _pos;
        }

        public int getUtil()
        {
            return util;
        }

        public Position getPos()
        {
            return pos;
        }
    }
}
