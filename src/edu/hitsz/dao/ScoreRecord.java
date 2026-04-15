package edu.hitsz.dao;

/**
 * 得分记录数据类
 * @author AircraftWar
 */
public class ScoreRecord {
    private String playerName;
    private int score;
    private long timestamp;
    private String difficulty;

    public ScoreRecord(String playerName, int score, long timestamp, String difficulty) {
        this.playerName = playerName;
        this.score = score;
        this.timestamp = timestamp;
        this.difficulty = difficulty;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-10d %-15d %s", 
            playerName, score, timestamp, difficulty);
    }
}
