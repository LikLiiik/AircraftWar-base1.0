package edu.hitsz.talent;

import java.util.ArrayList;
import java.util.List;

/**
 * 天赋系统管理类
 * @author AircraftWar
 */
public class TalentSystem {

    private static TalentSystem instance;
    private TalentDAO talentDAO;
    private List<Talent> allTalents;

    private TalentSystem() {
        this.talentDAO = new FileTalentDAO();
        initTalents();
    }

    public static TalentSystem getInstance() {
        if (instance == null) {
            instance = new TalentSystem();
        }
        return instance;
    }

    /**
     * 初始化所有天赋
     */
    private void initTalents() {
        allTalents = new ArrayList<>();
        allTalents.add(new Talent("life_boost", "生命强化", "初始HP+%d", 50, 1, 5));
        allTalents.add(new Talent("fire_boost", "火力强化", "子弹伤害+%d", 50, 1, 5));
        allTalents.add(new Talent("speed_boost", "极速射击", "射击周期-%d%%", 50, 1));
        allTalents.add(new Talent("luck_boost", "幸运之星", "道具掉落率+%d%%", 50, 1));
        allTalents.add(new Talent("defense_boost", "钢铁之躯", "受击伤害减免%d%%", 50, 1));
        allTalents.add(new Talent("dual_fire", "双重火力", "解锁双发子弹", 1, 3));
        allTalents.add(new Talent("rebirth", "不死鸟", "死亡时%d%%概率复活", 50, 1, 2));
    }

    /**
     * 获取所有天赋定义
     */
    public List<Talent> getAllTalents() {
        return allTalents;
    }

    /**
     * 加载玩家天赋数据
     */
    public PlayerTalent loadPlayer(String playerName) {
        PlayerTalent playerTalent = talentDAO.loadPlayerTalent(playerName);
        // 同步天赋等级到Talent对象
        for (Talent talent : allTalents) {
            int level = playerTalent.getTalentLevel(talent.getId());
            talent.setCurrentLevel(level);
        }
        return playerTalent;
    }

    /**
     * 保存玩家天赋数据
     */
    public boolean savePlayer(PlayerTalent playerTalent) {
        return talentDAO.savePlayerTalent(playerTalent);
    }

    /**
     * 添加玩家得分
     */
    public boolean addScore(String playerName, int score) {
        return talentDAO.addPlayerScore(playerName, score);
    }

    /**
     * 升级天赋
     */
    public boolean upgradeTalent(PlayerTalent playerTalent, String talentId) {
        if (playerTalent.upgradeTalent(talentId)) {
            // 同步到Talent对象
            for (Talent talent : allTalents) {
                if (talent.getId().equals(talentId)) {
                    talent.upgrade();
                    break;
                }
            }
            return savePlayer(playerTalent);
        }
        return false;
    }

    /**
     * 获取天赋效果值
     */
    public int getTalentEffect(String playerName, String talentId) {
        PlayerTalent playerTalent = loadPlayer(playerName);
        return playerTalent.getTalentLevel(talentId);
    }
}
