package edu.hitsz.strategy;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 超级火力加成策略实现
 * SuperFireProp 道具效果：环形散射
 */
public class FirePlusPowerUpStrategyImpl implements FirePowerUpStrategy {
    @Override
    public void activate(HeroAircraft hero) {
        hero.setShootStrategy(new RingShotStrategy());
    }
}
