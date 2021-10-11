/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ztq.guessnumber2.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ztq.guessnumber2.data.Dao;
import ztq.guessnumber2.dto.Game;
import ztq.guessnumber2.dto.Round;

/**
 *
 * @author tianqi.zhu
 */
@Component
public class Service {
    
    Dao dao;
    Random rd;

    @Autowired
    public Service(Dao dao) {
        this.dao = dao;
        rd = new Random();
    }
    
    // create a new round, auto fill result, timestamp, insert into dao
    // if guess hit target, update game
    public Round addRound(int gameId, String guessNum) {
        Round round = new Round();
        Game game = dao.getGame(gameId);
        
        round.setGameId(gameId);
        round.setGuessNum(guessNum);
        round.setTimestamp(new Date());
        
        String result = guessResult(game.getDaNumber(), guessNum);
        round.setResult(result);
        if (result.equals("e:4:p:0")) {
            game.setInProgress(false);
            dao.updateGame(game);
        }
        return dao.addRound(round);
    }
    
    // create new game, add to dao, return the generated gameId
    public int beginNewGame() {
        Game game = new Game();
        game.setDaNumber(daNumGenerator());
        game.setInProgress(true);
        return dao.addGame(game).getGameId();
    }
    
    public List<Game> getAll() {
        return dao.getAll().stream().map((i)->mask(i)).collect(Collectors.toList());
    }
    
    public Game getGame(int gameId) {
        return mask(dao.getGame(gameId));
    }
 
    public List<Round> getRounds(int gameId) {
        return dao.getRounds(gameId);
    }
    // randomly generate a 4 digits number
    private String daNumGenerator() {
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            set.add(rd.nextInt(10));
        }
        String ans = "";
        for (Integer x : set) {
            ans += x;
        }
        
        System.out.println("Generated daNumber: " + ans);
        
        return ans;
    }

    // generate a result String based on answer and guess
    private String guessResult(String answer, String guess) {
        int e = 0;
        Set<Character> ansSet = new HashSet<>(); 
        for (int i = 0; i < 4; i++) {
            ansSet.add(answer.charAt(i)); // put all char in answer in set
            if (answer.charAt(i) == guess.charAt(i)) {
                e++;    // exact the same
            }
        }
        
        char[] arr2 = guess.toCharArray();
        int p = 0;
        for (char x : arr2) {
            if (ansSet.contains(x)) {
                p++;    // ans set has it
            }
        }
        
        p -= e;
        return String.format("e:%d:p:%d", e, p);
    }
    
    // mask the daNumber of game in progress, used for mapping
    private Game mask(Game game) {
        if (game == null || !game.isInProgress())
            return game;
        Game copy = new Game();
        copy.setGameId(game.getGameId());
        copy.setDaNumber("****");
        copy.setInProgress(true);
        copy.setRounds(game.getRounds());
        return copy;
    }


}
