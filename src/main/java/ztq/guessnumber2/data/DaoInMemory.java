/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ztq.guessnumber2.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ztq.guessnumber2.dto.Game;
import ztq.guessnumber2.dto.Round;

/**
 *
 * @author tianqi.zhu
 */
@Repository
@Profile("memory")
public class DaoInMemory implements Dao{
    
    List<Game> games = new ArrayList<>();
    int maxIndex = 0;
    
    @Override
    public List<Game> getAll() {
        return games;
    }
    
    @Override
    public Game addGame(Game game) {
        game.setGameId(++maxIndex);
        games.add(game);
        return game;
    }
    
    @Override
    public List<Round> getRounds(int gameId) {
        Game game = games.stream()
                .filter((i) -> i.getGameId() == gameId)
                .findFirst().orElse(null);
        if (game == null)
            return null;
        return game.getRounds();        
    }
    
    @Override
    public Round addRound(Round round) {
        Game game = getGame(round.getGameId());
        round.setRoundId(game.getRounds().size());
        game.getRounds().add(round);
        return round;
    }

    @Override
    public Game getGame(int gameId) {
        return games.stream().filter((i)->(i.getGameId()==gameId)).findAny().orElse(null);
    }
    
    @Override
    public void updateGame(Game game) {
        Game old = getGame(game.getGameId());
        old.setInProgress(game.isInProgress());
    }
}
