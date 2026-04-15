package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 敌机单排直射策略
 * MobEnemy 和 EliteEnemy 使用
 */
public class EnemySingleRowStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + 30;
        int speedX = 0;
        int speedY = 15;
        
        BaseBullet bullet = new EnemyBullet(x, y, speedX, speedY, aircraft.getPower());
        res.add(bullet);
        
        return res;
    }
}
