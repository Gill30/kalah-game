package com.gill.kalah.service;

import com.gill.kalah.DAO.StateManager;
import com.gill.kalah.DTO.StateDTO;
import com.gill.kalah.exception.GameException;
import com.gill.kalah.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private StateManager stateManager;



    public StateDTO startNewGame() throws GameException {
        return stateManager.initializeGame();
    }


    public StateDTO play(String key, int box) throws  GameException {
        return stateManager.play( key, box);

    }


    public StateDTO getGameState( String key) {
        return stateManager.getGameState(key);
    }
}
