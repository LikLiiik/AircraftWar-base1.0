package edu.hitsz.talent;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于文件的天赋数据访问实现
 * @author AircraftWar
 */
public class FileTalentDAO implements TalentDAO {

    private static final String FILE_PATH = "data/talents/";
    private static final String FILE_SUFFIX = ".txt";

    public FileTalentDAO() {
        File dir = new File(FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public PlayerTalent loadPlayerTalent(String playerName) {
        PlayerTalent playerTalent = new PlayerTalent(playerName);
        String filePath = getFilePath(playerName);
        File file = new File(filePath);

        if (!file.exists()) {
            return playerTalent;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // 第一行：累计得分
            if ((line = reader.readLine()) != null) {
                playerTalent.setTotalScore(Integer.parseInt(line.trim()));
            }
            // 后续行：天赋ID,等级
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String talentId = parts[0].trim();
                    int level = Integer.parseInt(parts[1].trim());
                    playerTalent.setTalentLevel(talentId, level);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("读取天赋数据失败：" + playerName);
            e.printStackTrace();
        }

        return playerTalent;
    }

    @Override
    public boolean savePlayerTalent(PlayerTalent playerTalent) {
        String filePath = getFilePath(playerTalent.getPlayerName());
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // 第一行：累计得分
            writer.println(playerTalent.getTotalScore());
            // 后续行：天赋ID,等级
            for (Map.Entry<String, Integer> entry : getTalentMap(playerTalent).entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
            return true;
        } catch (IOException e) {
            System.err.println("保存天赋数据失败：" + playerTalent.getPlayerName());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addPlayerScore(String playerName, int score) {
        PlayerTalent playerTalent = loadPlayerTalent(playerName);
        playerTalent.addScore(score);
        return savePlayerTalent(playerTalent);
    }

    private String getFilePath(String playerName) {
        return FILE_PATH + playerName + FILE_SUFFIX;
    }

    /**
     * 获取天赋等级映射（通过反射或扩展PlayerTalent）
     * 这里简化处理，直接在PlayerTalent中添加getAllTalents方法
     */
    private Map<String, Integer> getTalentMap(PlayerTalent playerTalent) {
        // 使用反射获取私有字段，或者修改PlayerTalent添加getter
        // 这里我们修改PlayerTalent来支持
        return playerTalent.getAllTalentLevels();
    }
}
