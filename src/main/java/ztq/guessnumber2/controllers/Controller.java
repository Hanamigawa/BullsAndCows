/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ztq.guessnumber2.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ztq.guessnumber2.dto.Game;
import ztq.guessnumber2.dto.Round;
import ztq.guessnumber2.service.Service;

/**
 *
 * @author tianqi.zhu
 */
@RestController
@RequestMapping("/GNgame")
public class Controller {
    
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }
    
//    @GetMapping("/hello")
    @RequestMapping(path="/hello", method = RequestMethod.GET)
    public String helloworld() {
        return "hello world";
    }
    
//    @PostMapping
    @RequestMapping(path="/begin", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public int begin() {
        return service.beginNewGame();
    }
    
    @PostMapping("/guess")
//    @RequestMapping(path="/guess", method = RequestMethod.POST)
    public Round guess(@RequestParam int gameId, @RequestParam String guessNum) {
        System.out.println("gameId and guessNum received: " + gameId + " " + guessNum);
        return service.addRound(gameId, guessNum);
    }
    
//    @RequestMapping(path="/all", method = RequestMethod.GET)
    @GetMapping("/all")
    public List<Game> allGames() {
        return service.getAll();
    }
    
    @GetMapping("/game")
    public ResponseEntity<Game> getGame(@RequestParam int gameId) {
        Game game = service.getGame(gameId);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }
    
    @GetMapping("/rounds")
    public List<Round> getRounds(@RequestParam int gameId) {
        return service.getRounds(gameId);
    }
}
