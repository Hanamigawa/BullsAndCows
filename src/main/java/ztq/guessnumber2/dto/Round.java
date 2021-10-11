/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ztq.guessnumber2.dto;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author tianqi.zhu
 */
public class Round {
    
    int gameId;
    int roundId;
    String guessNum;
    String result;
    Date timestamp;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public String getGuessNum() {
        return guessNum;
    }

    public void setGuessNum(String guessNum) {
        this.guessNum = guessNum;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.gameId;
        hash = 47 * hash + this.roundId;
        hash = 47 * hash + Objects.hashCode(this.guessNum);
        hash = 47 * hash + Objects.hashCode(this.timestamp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.roundId != other.roundId) {
            return false;
        }
        if (!Objects.equals(this.guessNum, other.guessNum)) {
            return false;
        }
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        return true;
    }
    
    
}
