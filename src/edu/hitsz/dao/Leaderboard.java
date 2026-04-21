package edu.hitsz.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 排行榜管理类
 * @author AircraftWar
 */
public class Leaderboard {
    
    private ScoreDAO scoreDAO;
    private String currentDifficulty;
    private static final String DEFAULT_PLAYER_NAME = "Player";
    private static final int TOP_COUNT = 10;
    
    public Leaderboard() {
        this.scoreDAO = new FileScoreDAO();
        this.currentDifficulty = "default";
    }
    
    public Leaderboard(String difficulty) {
        this.scoreDAO = new FileScoreDAO();
        this.currentDifficulty = difficulty;
    }
    
    /**
     * 添加得分记录
     * @param score 得分
     */
    public void addRecord(int score) {
        long timestamp = System.currentTimeMillis();
        ScoreRecord record = new ScoreRecord(DEFAULT_PLAYER_NAME, score, timestamp, currentDifficulty);
        scoreDAO.save(record);
    }
    
    /**
     * 添加得分记录（指定难度）
     * @param score 得分
     * @param difficulty 难度
     */
    public void addRecord(int score, String difficulty) {
        long timestamp = System.currentTimeMillis();
        ScoreRecord record = new ScoreRecord(DEFAULT_PLAYER_NAME, score, timestamp, difficulty);
        scoreDAO.save(record);
    }
    
    /**
     * 添加得分记录（指定玩家名和难度）
     * @param score 得分
     * @param playerName 玩家名
     * @param difficulty 难度
     */
    public void addRecord(int score, String playerName, String difficulty) {
        long timestamp = System.currentTimeMillis();
        ScoreRecord record = new ScoreRecord(playerName, score, timestamp, difficulty);
        scoreDAO.save(record);
    }
    
    /**
     * 打印当前难度的排行榜
     */
    public void printLeaderboard() {
        printLeaderboard(currentDifficulty);
    }
    
    /**
     * 打印指定难度的排行榜
     * @param difficulty 难度
     */
    public void printLeaderboard(String difficulty) {
        if (scoreDAO instanceof FileScoreDAO) {
            List<ScoreRecord> topRecords = ((FileScoreDAO) scoreDAO).getTopRecordsByDifficulty(difficulty, TOP_COUNT);
            printLeaderboard(topRecords, difficulty);
        }
    }
    
    /**
     * 打印所有难度的总排行榜
     */
    public void printAllLeaderboard() {
        List<ScoreRecord> topRecords = scoreDAO.getTopRecords(TOP_COUNT);
        System.out.println("\n========== 总排行榜 (Top " + TOP_COUNT + ") ==========");
        printLeaderboardHeader();
        int rank = 1;
        for (ScoreRecord record : topRecords) {
            System.out.printf("%-8d %s%n", rank++, formatRecord(record));
        }
        System.out.println("==========================================\n");
    }
    
    /**
     * 打印排行榜
     * @param records 记录列表
     * @param difficulty 难度
     */
    private void printLeaderboard(List<ScoreRecord> records, String difficulty) {
        System.out.println("\n========== " + getDifficultyName(difficulty) + "排行榜 (Top " + TOP_COUNT + ") ==========");
        printLeaderboardHeader();
        int rank = 1;
        for (ScoreRecord record : records) {
            System.out.printf("%-8d %s%n", rank++, formatRecord(record));
        }
        System.out.println("==========================================\n");
    }
    
    /**
     * 打印排行榜表头
     */
    private void printLeaderboardHeader() {
        System.out.printf("%-8s %-15s %-10s %-15s %s%n", "名次", "玩家名", "得分", "时间", "难度");
        System.out.println("----------------------------------------------------------");
    }
    
    /**
     * 格式化记录（不包含难度）
     * @param record 记录
     * @return 格式化字符串
     */
    private String formatRecord(ScoreRecord record) {
        String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(record.getTimestamp()));
        return String.format("%-15s %-10d %-15s %s",
            record.getPlayerName(),
            record.getScore(),
            timeStr,
            record.getDifficulty());
    }
    
    /**
     * 获取难度名称
     * @param difficulty 难度标识
     * @return 难度名称
     */
    private String getDifficultyName(String difficulty) {
        switch (difficulty) {
            case "easy":
                return "简单难度";
            case "normal":
                return "普通难度";
            case "hard":
                return "困难难度";
            default:
                return "默认难度";
        }
    }
    
    public void setCurrentDifficulty(String difficulty) {
        this.currentDifficulty = difficulty;
    }
    
    public String getCurrentDifficulty() {
        return currentDifficulty;
    }
    
    /**
     * 获取前 N 条得分记录
     * @param top 记录数量
     * @return 前 N 条得分记录
     */
    public List<ScoreRecord> getTopRecords(int top) {
        if (scoreDAO instanceof FileScoreDAO) {
            return ((FileScoreDAO) scoreDAO).getTopRecordsByDifficulty(currentDifficulty, top);
        }
        return new java.util.ArrayList<>();
    }
}
