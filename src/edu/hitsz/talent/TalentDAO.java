package edu.hitsz.talent;

/**
 * 天赋数据访问接口
 * @author AircraftWar
 */
public interface TalentDAO {

    /**
     * 加载玩家天赋数据
     * @param playerName 玩家名
     * @return 玩家天赋数据
     */
    PlayerTalent loadPlayerTalent(String playerName);

    /**
     * 保存玩家天赋数据
     * @param playerTalent 玩家天赋数据
     * @return 保存是否成功
     */
    boolean savePlayerTalent(PlayerTalent playerTalent);

    /**
     * 添加玩家得分
     * @param playerName 玩家名
     * @param score 得分
     * @return 是否成功
     */
    boolean addPlayerScore(String playerName, int score);
}
