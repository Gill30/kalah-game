package com.gill.kalah.model;

import org.springframework.stereotype.Component;

@Component
public class State {
    private int[] board;
    private GameStatus gameStatus;
    public State() {
        this.gameStatus = GameStatus.NEW;
        this.board = new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
}
