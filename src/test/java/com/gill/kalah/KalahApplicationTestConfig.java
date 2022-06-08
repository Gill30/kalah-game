package com.gill.kalah;

import com.gill.kalah.DAO.StateManager;
import com.gill.kalah.model.GameStatus;
import com.gill.kalah.model.State;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KalahApplicationTestConfig {

    @Bean
    public State state(){
        State gameState = new State();
        gameState.setGameStatus(GameStatus.P1);
        gameState.setBoard(new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0});
        return gameState;
    }
    @Bean
    public StateManager stateManager(State state){
        StateManager stateManager = new StateManager();
        return stateManager;
    }
}
