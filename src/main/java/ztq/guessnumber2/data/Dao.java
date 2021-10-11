/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztq.guessnumber2.data;

import java.util.List;
import ztq.guessnumber2.dto.Game;
import ztq.guessnumber2.dto.Round;

/**
 *
 * @author zwhet
 */
public interface Dao {
    Game addGame(Game game);
    Round addRound(Round round);
    List<Game> getAll();
    Game getGame(int gameId);
    List<Round> getRounds(int gameId);
    void updateGame(Game game);
}
