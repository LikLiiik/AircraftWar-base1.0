package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 环射策略
 * SuperFireProp 道具效果：环形散射
 */
public class RingShotStrategy implements ShootStrategy {
    private static final int BULLET_COUNT = 8;
    private static final int BULLET_SPEED = 5;
    
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        
        // 均匀分布 8 发子弹，形成环形
        for (int i = 0; i < BULLET_COUNT; i++) {
            // 计算每个子弹的角度（360度均匀分布）
            double angle = (2 * Math.PI * i) / BULLET_COUNT;
            
            // 计算子弹速度分量
            double speedX = Math.sin(angle) * BULLET_SPEED;
            double speedY = Math.cos(angle) * BULLET_SPEED;
            
            res.add(new HeroBullet(x, y, (int)speedX, (int)speedY, aircraft.getPower()));
        }
        
        return res;
    }
}
