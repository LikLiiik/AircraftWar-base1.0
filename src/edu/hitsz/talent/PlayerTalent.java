package edu.hitsz.talent;

import java.util.HashMap;
import java.util.Map;

/**
 * 玩家天赋数据
 * @author AircraftWar
 */
public class PlayerTalent {
    private String playerName;
    private int totalScore;
    private Map<String, Integer> talentLevels;

    public PlayerTalent(String playerName) {
        this.playerName = playerName;
        this.totalScore = 0;
        this.talentLevels = new HashMap<>();
    }

    public String getPlayerName() { return playerName; }
    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int score) { this.totalScore = score; }

    /**
     * 增加累计得分
     */
    public void addScore(int score) {
        this.totalScore += score;
    }

    /**
     * 获取可用天赋点
     */
    public int getAvailableTalentPoints() {
        int usedPoints = 0;
        for (int level : talentLevels.values()) {
            usedPoints += level;
        }
        return (totalScore / 10000) - usedPoints;
    }

    /**
     * 获取天赋等级
     */
    public int getTalentLevel(String talentId) {
        return talentLevels.getOrDefault(talentId, 0);
    }

    /**
     * 设置天赋等级
     */
    public void setTalentLevel(String talentId, int level) {
        talentLevels.put(talentId, level);
    }

    /**
     * 升级天赋
     */
    public boolean upgradeTalent(String talentId) {
        int currentLevel = getTalentLevel(talentId);
        if (getAvailableTalentPoints() > 0) {
            setTalentLevel(talentId, currentLevel + 1);
            return true;
        }
        return false;
    }

    /**
     * 获取所有天赋等级
     */
    public Map<String, Integer> getAllTalentLevels() {
        return new HashMap<>(talentLevels);
    }
}
