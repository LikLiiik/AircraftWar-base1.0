package edu.hitsz.dao;

import java.io.*;
import java.util.*;

/**
 * 基于文件的得分记录 DAO 实现
 * @author AircraftWar
 */
public class FileScoreDAO implements ScoreDAO {
    
    private static final String FILE_PATH = "data/scores/";
    private static final String FILE_PREFIX = "score_";
    private static final String FILE_SUFFIX = ".txt";
    
    public FileScoreDAO() {
        // 确保目录存在
        File dir = new File(FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    @Override
    public boolean save(ScoreRecord record) {
        String filePath = getFilePath(record.getDifficulty());
        List<ScoreRecord> records = readFile(filePath);
        records.add(record);
        return writeFile(filePath, records);
    }
    
    @Override
    public List<ScoreRecord> loadAll() {
        List<ScoreRecord> allRecords = new ArrayList<>();
        File dir = new File(FILE_PATH);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.startsWith(FILE_PREFIX) && name.endsWith(FILE_SUFFIX));
            if (files != null) {
                for (File file : files) {
                    allRecords.addAll(readFile(file.getAbsolutePath()));
                }
            }
        }
        return allRecords;
    }
    
    @Override
    public List<ScoreRecord> getTopRecords(int top) {
        List<ScoreRecord> allRecords = loadAll();
        // 按分数降序排序
        allRecords.sort((r1, r2) -> Integer.compare(r2.getScore(), r1.getScore()));
        // 返回前 top 条记录
        int size = Math.min(top, allRecords.size());
        return allRecords.subList(0, size);
    }
    
    /**
     * 根据难度获取文件路径
     * @param difficulty 难度
     * @return 文件路径
     */
    private String getFilePath(String difficulty) {
        return FILE_PATH + FILE_PREFIX + difficulty + FILE_SUFFIX;
    }
    
    /**
     * 从文件读取得分记录
     * @param filePath 文件路径
     * @return 得分记录列表
     */
    private List<ScoreRecord> readFile(String filePath) {
        List<ScoreRecord> records = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return records;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String playerName = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    long timestamp = Long.parseLong(parts[2]);
                    String difficulty = parts.length > 3 ? parts[3] : "default";
                    records.add(new ScoreRecord(playerName, score, timestamp, difficulty));
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件失败：" + filePath);
            e.printStackTrace();
        }
        return records;
    }
    
    /**
     * 写入得分记录到文件
     * @param filePath 文件路径
     * @param records 得分记录列表
     * @return 写入是否成功
     */
    private boolean writeFile(String filePath, List<ScoreRecord> records) {
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (ScoreRecord record : records) {
                writer.printf("%s,%d,%d,%s%n",
                    record.getPlayerName(),
                    record.getScore(),
                    record.getTimestamp(),
                    record.getDifficulty());
            }
            return true;
        } catch (IOException e) {
            System.err.println("写入文件失败：" + filePath);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 根据难度加载得分记录
     * @param difficulty 难度
     * @return 得分记录列表
     */
    public List<ScoreRecord> loadByDifficulty(String difficulty) {
        String filePath = getFilePath(difficulty);
        return readFile(filePath);
    }
    
    /**
     * 获取指定难度的前 N 条记录
     * @param difficulty 难度
     * @param top 记录数量
     * @return 前 N 条得分记录
     */
    public List<ScoreRecord> getTopRecordsByDifficulty(String difficulty, int top) {
        List<ScoreRecord> records = loadByDifficulty(difficulty);
        records.sort((r1, r2) -> Integer.compare(r2.getScore(), r1.getScore()));
        int size = Math.min(top, records.size());
        return records.subList(0, size);
    }

    @Override
    public boolean delete(ScoreRecord record) {
        String filePath = getFilePath(record.getDifficulty());
        List<ScoreRecord> records = readFile(filePath);
        boolean removed = records.removeIf(r -> 
            r.getPlayerName().equals(record.getPlayerName()) &&
            r.getScore() == record.getScore() &&
            r.getTimestamp() == record.getTimestamp()
        );
        if (removed) {
            return writeFile(filePath, records);
        }
        return false;
    }
}
