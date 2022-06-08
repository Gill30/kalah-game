package com.gill.kalah;

import com.gill.kalah.DAO.StateManager;
import com.gill.kalah.DTO.StateDTO;
import com.gill.kalah.cache.CacheManager;
import com.gill.kalah.exception.GameException;
import com.gill.kalah.model.GameStatus;
import com.gill.kalah.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;


@SpringJUnitConfig(KalahApplicationTestConfig.class)
public class StateManagerTests {
    @Autowired
    private State gameState;

    @Autowired
    private StateManager stateManager;


    @BeforeEach
    public void setupTest(){
        gameState.setGameStatus(GameStatus.P1);
        gameState.setBoard(new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0});
        CacheManager.getInstance().cache.put("test", gameState);

    }

    @DisplayName("initializeGameTest")
    @Test
    public void initializeGameTest() throws Exception{

        //checking with default data
        State expectedState = new State();
        expectedState.setGameStatus(GameStatus.P1);
        expectedState.setBoard(new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0});
        StateDTO resultState = stateManager.initializeGame();
        Assertions.assertArrayEquals(resultState.getBoard(), expectedState.getBoard());
        Assertions.assertEquals(resultState.getGameStatus(), expectedState.getGameStatus());

        //checking with changed data
        //checking with default data
        expectedState = new State();
        expectedState.setGameStatus(GameStatus.P1);
        gameState.setGameStatus(GameStatus.TIED);
        expectedState.setBoard(new int[]{6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0});
        resultState = stateManager.initializeGame();
        Assertions.assertArrayEquals(resultState.getBoard(), expectedState.getBoard());
        Assertions.assertEquals(resultState.getGameStatus(), expectedState.getGameStatus());
    }

    @DisplayName("Testing play funciton - Normal Flow")
    @Test
    public void playTest() throws Exception{

        //normal flow
        State expectedState = new State();
        expectedState.setGameStatus(GameStatus.P1);
        expectedState.setBoard(new int[]{0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0});
        StateDTO resultState = stateManager.play("test", 0);
        Assertions.assertArrayEquals(resultState.getBoard(), expectedState.getBoard());
        Assertions.assertEquals(resultState.getGameStatus(), expectedState.getGameStatus());

        //flow to collect opposite stones
        expectedState = new State();
        expectedState.setGameStatus(GameStatus.P2);
        expectedState.setBoard(new int[]{0, 7, 7, 7, 0, 0, 7, 0, 6, 6, 6, 6, 6, 0});
        gameState.setBoard(new int[]{0, 7, 7, 7, 1, 0, 0, 6, 6, 6, 6, 6, 6, 0});
        resultState = stateManager.play("test", 4);
        Assertions.assertArrayEquals(resultState.getBoard(), expectedState.getBoard());
        Assertions.assertEquals(resultState.getGameStatus(), expectedState.getGameStatus());

    }

    @DisplayName("Testing play funciton - collect opposite stones")
    @Test
    public void playTestCollectOppStones() throws Exception{

        //flow to collect opposite stones
        State expectedState = new State();
        expectedState.setGameStatus(GameStatus.P2);
        expectedState.setBoard(new int[]{0, 7, 7, 7, 0, 0, 7, 0, 6, 6, 6, 6, 6, 0});
        gameState.setBoard(new int[]{0, 7, 7, 7, 1, 0, 0, 6, 6, 6, 6, 6, 6, 0});
        StateDTO resultState = stateManager.play("test",4);
        Assertions.assertArrayEquals(resultState.getBoard(), expectedState.getBoard());
        Assertions.assertEquals(resultState.getGameStatus(), expectedState.getGameStatus());

    }

    @DisplayName("Testing play function - box number out of range")
    @Test
    public void playTestIllegalBoxNumber() throws Exception{
        //illegal box number
        Throwable exception = Assertions.assertThrows(GameException.class, () -> {
            stateManager.play("test", 15);
        });
        Assertions.assertEquals("Error :  Illegal box", exception.getMessage());
    }

    @DisplayName("Testing play function - Invalid box for P1")
    @Test
    public void playTestIllegalBoxForP1() throws Exception{
        //illegal box for P1
        Throwable exception = Assertions.assertThrows(GameException.class, () -> {
            stateManager.play("test", 10);
        });
        Assertions.assertEquals("Error :  Illegal box", exception.getMessage());
    }

    @DisplayName("Testing play function - Winner Flow")
    @Test
    public void playTestWinnerFlow() throws Exception{
        State expectedState = new State();
        expectedState.setGameStatus(GameStatus.P2WON);
        gameState.setBoard(new int[]{0, 0, 0, 0, 0, 1, 7, 0, 6, 6, 6, 6, 6, 30});
        expectedState.setBoard(new int[]{0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 60});
        StateDTO resultState = stateManager.play("test", 5);
        Assertions.assertArrayEquals(resultState.getBoard(), expectedState.getBoard());
        Assertions.assertEquals(resultState.getGameStatus(), expectedState.getGameStatus());

    }

    @DisplayName("Testing play function - Tied Flow")
    @Test
    public void playTestGameTiedFlow() throws Exception{
        State expectedState = new State();
        expectedState.setGameStatus(GameStatus.TIED);
        gameState.setBoard(new int[]{0, 0, 0, 0, 0, 1, 7, 0, 1, 1, 1, 1, 1, 3});
        expectedState.setBoard(new int[]{0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 8});
        StateDTO resultState = stateManager.play("test", 5);
        Assertions.assertArrayEquals(resultState.getBoard(), expectedState.getBoard());
        Assertions.assertEquals(resultState.getGameStatus(), expectedState.getGameStatus());
    }
}
