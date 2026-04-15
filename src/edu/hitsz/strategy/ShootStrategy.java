package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 射击策略接口
 * 定义不同机型的弹道发射逻辑
 */
public interface ShootStrategy {
    /**
     * 射击方法
     * @param aircraft 发射子弹的飞机
     * @return 子弹列表
     */
    List<BaseBullet> shoot(AbstractAircraft aircraft);
}
