package com.gill.kalah.Assembler;

import com.gill.kalah.DTO.StateDTO;
import com.gill.kalah.model.State;

public class StateToStateDTOAssembler {
    public static StateDTO stateToStateDTO(State state, String key){
        return new StateDTO(
                state.getBoard(),
                state.getGameStatus(),
                key
        );
    }
}
