package edu.hitsz.strategy;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 火力加成策略接口
 * 定义不同火力道具的效果
 */
public interface FirePowerUpStrategy {
    /**
     * 激活火力加成效果
     * @param hero 英雄机
     */
    void activate(HeroAircraft hero);
}
