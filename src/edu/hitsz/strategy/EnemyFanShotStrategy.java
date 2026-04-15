package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 敌机扇形散射策略
 * AceEnemy 使用
 */
public class EnemyFanShotStrategy implements ShootStrategy {
    private static final int ANGLE_SPACING = 15;
    
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + 30;
        
        // 中间子弹（垂直向下）
        res.add(new EnemyBullet(x, y, 0, 15, aircraft.getPower()));
        
        // 左侧子弹（向左下方）
        double angleLeft = Math.toRadians(ANGLE_SPACING);
        double speedXLeft = -Math.sin(angleLeft) * 15;
        double speedYLeft = Math.cos(angleLeft) * 15;
        res.add(new EnemyBullet(x, y, (int)speedXLeft, (int)speedYLeft, aircraft.getPower()));
        
        // 右侧子弹（向右下方）
        double angleRight = Math.toRadians(ANGLE_SPACING);
        double speedXRight = Math.sin(angleRight) * 15;
        double speedYRight = Math.cos(angleRight) * 15;
        res.add(new EnemyBullet(x, y, (int)speedXRight, (int)speedYRight, aircraft.getPower()));
        
        return res;
    }
}
