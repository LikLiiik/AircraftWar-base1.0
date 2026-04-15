package edu.hitsz.dao;

import java.util.List;

/**
 * 得分记录 DAO 接口
 * @author AircraftWar
 */
public interface ScoreDAO {
    
    /**
     * 保存得分记录
     * @param record 得分记录
     * @return 保存是否成功
     */
    boolean save(ScoreRecord record);
    
    /**
     * 加载所有得分记录
     * @return 得分记录列表
     */
    List<ScoreRecord> loadAll();
    
    /**
     * 获取前 N 条得分记录（按分数降序排序）
     * @param top 记录数量
     * @return 前 N 条得分记录
     */
    List<ScoreRecord> getTopRecords(int top);
}
