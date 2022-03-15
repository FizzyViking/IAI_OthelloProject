import java.util.ArrayList;

/**
 * A minmax implementation of an Othello AI The method to decide the next move
 * returns the best move determined by the minmax algorithm
 * and terminates if there are no legal moves left
 * or if the depth limit has been reached
 * @author Mai Ajspur og mig
 * @version 9.2.2018
 */
public class NotAsDumAI implements IOthelloAI{

    public Position decideMove(GameState s){
        System.out.println("NotAsDumbAI choosing");

        var startTime = System.nanoTime();
        var pos =  MaxValue(s, Integer.MIN_VALUE, Integer.MAX_VALUE, 6, s.getPlayerInTurn()).getPos();
        var endTime = System.nanoTime();
        System.out.println("NotAsDumbAI chooses: " + pos.toString());
        System.out.println("It took " + ((endTime-startTime)/1_000_000_000) + " seconds");

        return pos;
    }

    public int CalculateScore(GameState state, int playerID){
        if (playerID == 1){
            return state.countTokens()[0] - state.countTokens()[1];
        }else {
            return state.countTokens()[1] - state.countTokens()[0];
        }
    }

    public Pos MaxValue(GameState state, int alpha, int beta, int depth, int playerID){
        var legalMoves = state.legalMoves();
        if (legalMoves.isEmpty() || depth < 1 ){
            return new Pos(CalculateScore(state, playerID), null);
        }

        Position nextMove = null;
        int value = Integer.MIN_VALUE;

        for (Position a : legalMoves) {

            GameState _state = new GameState(state.getBoard(), state.getPlayerInTurn());
            _state.insertToken(a);

            Pos next = MinValue(_state, alpha, beta, depth-1, playerID);

            if (next.getUtil() > value) {
                value = next.getUtil();
                alpha = Math.max(alpha, value);
                nextMove = a;
            }
            if (value >= beta) {
                return new Pos(value, nextMove);
            }
        }
        return new Pos(value, nextMove);
    }

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

    class Pos {
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
