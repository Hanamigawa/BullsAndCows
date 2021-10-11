/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztq.guessnumber2.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ztq.guessnumber2.dto.Game;
import ztq.guessnumber2.dto.Round;

/**
 *
 * @author tianqi.zhu
 */
@Repository
@Profile("database")
public class DaoDB implements Dao {

    @Autowired
    JdbcTemplate jdbc;

    public static final class GameMapper implements RowMapper<Game> {
        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setDaNumber(rs.getString("daNumber"));
            game.setInProgress(rs.getBoolean("inProgress"));
            return game;
        }
    }
    
    private static final class RoundMapper implements RowMapper<Round> {
        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGameId(rs.getInt("gameId"));
            round.setGuessNum(rs.getString("guessNum"));
            round.setTimestamp(rs.getDate("timeOfRound"));
            round.setResult(rs.getString("result"));
            return round;
        }
    }

    @Override
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO Game(daNumber, inProgress) VALUES(?,?)";
        jdbc.update(INSERT_GAME, 
                game.getDaNumber(), 
                game.isInProgress());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
        return game;
    }

    @Override
    public Round addRound(Round round) {
        Game game = getGame(round.getGameId());
        if (game == null) 
            return null;
        
        final String QUERY = "INSERT INTO Round SET guessNum=?, gameId=?, timeOfRound=?, result=?";
        jdbc.update(QUERY, round.getGuessNum(), round.getGameId(), round.getTimestamp(), round.getResult());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newId);
        return round;
    }

    @Override
    public List<Game> getAll() {
        final String SELECT_ALL_GAMES = "SELECT * FROM Game";
        List<Game> games = jdbc.query(SELECT_ALL_GAMES, new GameMapper());
        for (Game game : games) {
            game.setRounds(getRounds(game.getGameId()));
        }
        return games;
    }

    @Override
    public Game getGame(int gameId) {
        final String SELECT_GAME_BY_ID = "SELECT * FROM Game WHERE gameId = ?";
        Game game = jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
        game.setRounds(getRounds(gameId));
        return game;
    }

    @Override
    public List<Round> getRounds(int gameId) {
        final String QUERY = "SELECT * FROM Round WHERE gameId=?";
        return jdbc.query(QUERY, new RoundMapper(), gameId);
    }

    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE Game SET daNumber = ?, inProgress = ? WHERE gameId = ?";
        jdbc.update(UPDATE_GAME,
                game.getDaNumber(),
                game.isInProgress(),
                game.getGameId());
    }

}
