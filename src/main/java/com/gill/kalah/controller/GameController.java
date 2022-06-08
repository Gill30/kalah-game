package com.gill.kalah.controller;

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

    @GetMapping("/getGameState")
    public  @ResponseBody ResponseEntity<State> getGameState(){
        System.out.println(java.util.UUID.randomUUID());
        return new ResponseEntity<>(gameService.getGameState(), HttpStatus.OK);
    }

    @GetMapping("/newGame")
    public @ResponseBody
    ResponseEntity startNewGame() {
        try {
            return new ResponseEntity<State>(gameService.startNewGame(), HttpStatus.OK);
        } catch (GameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/play/{box}")
    public @ResponseBody
    ResponseEntity play(@PathVariable("box") int box) {
        try {
            return new ResponseEntity<State>(gameService.play(box), HttpStatus.OK);
        } catch (GameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

