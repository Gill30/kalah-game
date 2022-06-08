package com.gill.kalah.DAO;

import com.gill.kalah.exception.GameException;
import com.gill.kalah.model.GameStatus;
import com.gill.kalah.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.IntStream;

@Component
public class StateManager {
    private final int P1_KALAH = 6;
    private final int P2_KALAH = 13;

    @Autowired
    private State gameState;

    public State initializeGame() throws GameException {
        synchronized (gameState) {
            gameState = new State();
            gameState.setGameStatus(GameStatus.P1);
        }
        return gameState;
    }

    /**
     * Returns state, after completing the turn.
     *
     * @param box box player have selected for turn.
     * @return state
     */
    public State play( int box) throws GameException {
        State state = this.gameState;
        GameStatus oppositePlayer = null;
        int oppositePlayerKalah = 0;
        int currentPlayerKalah = 0;
        GameStatus currentStatus = this.gameState.getGameStatus();
        if(currentStatus == GameStatus.P1){
            oppositePlayer = GameStatus.P2;
            currentPlayerKalah  = P1_KALAH;
            oppositePlayerKalah = P2_KALAH;
        }else if(currentStatus == GameStatus.P2){
            oppositePlayer = GameStatus.P1;
            oppositePlayerKalah = P1_KALAH;
            currentPlayerKalah  = P2_KALAH;
        }

        validateMove(state,  box);
        synchronized (gameState) {
            int index = updatedBoard(state, oppositePlayerKalah, box);

            if (index == currentPlayerKalah) {
                //need to re check logic here
                if (isGameOver(state)) {
                    state = this.endGame(state);
                }
                this.gameState = state;
                return this.gameState;
            } else if (state.getBoard()[index] == 1) {
                state = oppositeBoxStoneCollMoveCheck(state, index, currentPlayerKalah);
            }
            if (isGameOver(state)) {
                state = this.endGame(state);
            } else {
                state.setGameStatus(oppositePlayer);
            }
            this.gameState = state;
        }
        return this.gameState;
    }

    public State endGame(State state)  {
        int[] board = state.getBoard();

        int p1Leftovers = IntStream.of(Arrays.copyOfRange(board, 0, P1_KALAH)).sum();
        int p2Leftovers = IntStream.of(Arrays.copyOfRange(board, 7, P2_KALAH)).sum();

        int p1Score = board[P1_KALAH] + p1Leftovers;
        int p2score = board[P2_KALAH] + p2Leftovers;

        board = new int[]{0, 0, 0, 0, 0, 0, p1Score, 0, 0, 0, 0, 0, 0, p2score};
        state.setBoard(board);

        return findWinner(state);
    }

    public State findWinner(State state)  {
        int[] board = state.getBoard();
        if (board[P1_KALAH] > board[P2_KALAH]) {
            state.setGameStatus(GameStatus.P1WON);
        } else if (board[P2_KALAH] > board[P1_KALAH]) {
            state.setGameStatus(GameStatus.P2WON);
        } else {
            state.setGameStatus(GameStatus.TIED);
        }
        return state;
    }

    protected void validateMove(State state, int box) throws GameException {
        GameStatus currStatus = state.getGameStatus();
        if (box < 0 || box == P1_KALAH || box >= P2_KALAH) {
            throw new GameException(" Illegal box");
        } else if (box < P1_KALAH && currStatus == GameStatus.P2 || (box > P1_KALAH && currStatus == GameStatus.P1)) {
            throw  new GameException(" Illegal box");
        } else {
            int[] board = state.getBoard();
            if (board[box] == 0) {
                throw new GameException("There's no stone in pit " + box + ".");
            }
        }
    }

    /**
     * Sows stones counter clockwise.
     *
     * @param  state          Current state instance.
     * @param opponentKalah Opponent's kalah ID.
     * @param box           Which pit player is starting to sow stones from.
     * @return Returns the ID of the pit where it puts its last stone.
     */
    protected int updatedBoard(State state, int opponentKalah, int box) {
        int[] board = state.getBoard();
        int stoneCount = board[box];
        int currentIndex = box;

        board[box] = 0;
        while (stoneCount > 0) {
            currentIndex = (currentIndex + 1) % 14;
            if (currentIndex != opponentKalah) {
                board[currentIndex]++;
                stoneCount--;
            }
        }
        state.setBoard(board);
        return currentIndex;
    }

    /**
     * Returns if game is over. Checks if there are stones either sides.
     *
     * @param state Current game state instance.
     * @return Returns if the game is over or not.
     */
    public boolean isGameOver(State state) {
        int[] board = state.getBoard();
        int[] player1boxes = Arrays.copyOfRange(board, 0, P1_KALAH);
        int[] player2boxes = Arrays.copyOfRange(board, 7, P2_KALAH);
        return IntStream.of(player1boxes).allMatch(x -> x == 0) || IntStream.of(player2boxes ).allMatch(x -> x == 0);
    }

    /**
     * Returns state, after checking if we have to collect opposite player oppoxite box stones.
     *
     * @param state Current game state instance.
     * @param lastIndex index of last sowed stone.
     * @param curPlayerKalah kalah of current player
     * @return state
     */
    public State oppositeBoxStoneCollMoveCheck(State state,   int lastIndex, int curPlayerKalah){
        if (lastIndex < P1_KALAH && state.getGameStatus() ==  GameStatus.P1) {
            return collectStones(state, lastIndex, curPlayerKalah);
        }else if (lastIndex > P1_KALAH && state.getGameStatus() ==  GameStatus.P2){
            return collectStones(state, lastIndex, curPlayerKalah);
        }
        return state;
    }

    /**
     * Returns state, after collecting opposite player opposite box stones.
     *
     * @param state Current game state instance.
     * @param lastIndex index of last sowed stone.
     * @param curPlayerKalah kalah of current player
     * @return updated game state
     */
    public State collectStones(State state, int lastIndex, int curPlayerKalah){
        int[] board = state.getBoard();
        int collectedStones = board[12 - lastIndex];
        board[12 - lastIndex] = 0;
        board[lastIndex] = 0;
        board[curPlayerKalah] = board[curPlayerKalah] + collectedStones + 1;
        state.setBoard(board);
        return state;
    }

    public void setGameState(State state){
        this.gameState = state;
    }

    public State getGameState() {
        return this.gameState;
    }
}
