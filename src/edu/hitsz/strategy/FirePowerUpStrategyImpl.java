package edu.hitsz.strategy;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 火力加成策略实现
 * FireProp 道具效果：双发散射
 */
public class FirePowerUpStrategyImpl implements FirePowerUpStrategy {
    @Override
    public void activate(HeroAircraft hero) {
        hero.setShootStrategy(new TripleSpreadStrategy());
    }
}
