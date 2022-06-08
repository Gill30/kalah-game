package com.gill.kalah.service;

import com.gill.kalah.DAO.StateManager;
import com.gill.kalah.exception.GameException;
import com.gill.kalah.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private StateManager stateManager;



    public synchronized State startNewGame() throws GameException {
        return stateManager.initializeGame();
    }


    public synchronized State play(int box) throws  GameException {
        return stateManager.play( box);

    }


    public State getGameState() {
        return stateManager.getGameState();
    }
}
