 package com.gill.kalah.controller;

import com.gill.kalah.DTO.StateDTO;
import com.gill.kalah.exception.GameException;
import com.gill.kalah.model.State;
import com.gill.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class GameController {
    @Autowired
    private GameService gameService;


    @GetMapping("/")
    public  String getHome(){
        return "index";
    }

    @GetMapping("/getGameState/{key}")
    public  @ResponseBody ResponseEntity<StateDTO> getGameState(@PathVariable("key") String key){
        System.out.println();
        return new ResponseEntity<>(gameService.getGameState(key), HttpStatus.OK);
    }

    @GetMapping("/newGame")
    public @ResponseBody
    ResponseEntity startNewGame() {
        try {
            return new ResponseEntity<StateDTO>(gameService.startNewGame(), HttpStatus.OK);
        } catch (GameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/play/{key}/{box}")
    public @ResponseBody
    ResponseEntity play(@PathVariable("key") String key , @PathVariable("box") int box) {
        try {
            return new ResponseEntity<StateDTO>(gameService.play(key, box), HttpStatus.OK);
        } catch (GameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

