package com.gill.kalah.DTO;

import com.gill.kalah.model.GameStatus;

public class StateDTO {
    private int[] board;
    private GameStatus gameStatus;
    private String Key;

    public StateDTO(int[] board, GameStatus gameStatus, String key) {
        this.board = board;
        this.gameStatus = gameStatus;
        Key = key;
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

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
