package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 敌机双排直射策略
 * ElitePlusEnemy 使用
 */
public class EnemyDoubleRowStrategy implements ShootStrategy {
    private static final int BULLET_SPACING = 30;
    
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + 30;
        int speedY = 15;
        
        // 左排子弹
        BaseBullet bullet1 = new EnemyBullet(x - BULLET_SPACING / 2, y, 0, speedY, aircraft.getPower());
        // 右排子弹
        BaseBullet bullet2 = new EnemyBullet(x + BULLET_SPACING / 2, y, 0, speedY, aircraft.getPower());
        
        res.add(bullet1);
        res.add(bullet2);
        
        return res;
    }
}
