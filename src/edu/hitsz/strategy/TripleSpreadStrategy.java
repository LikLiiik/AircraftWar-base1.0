package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 三发散射策略
 * SuperFireProp 道具效果
 */
public class TripleSpreadStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection() * 5;
        
        // 中间子弹（垂直向上）
        BaseBullet bullet1 = new HeroBullet(x, y, 0, speedY, aircraft.getPower());
        
        // 左侧子弹（向左上方）
        int speedXLeft = -3;
        BaseBullet bullet2 = new HeroBullet(x - 5, y, speedXLeft, speedY, aircraft.getPower());
        
        // 右侧子弹（向右上方）
        int speedXRight = 3;
        BaseBullet bullet3 = new HeroBullet(x + 5, y, speedXRight, speedY, aircraft.getPower());
        
        res.add(bullet1);
        res.add(bullet2);
        res.add(bullet3);
        
        return res;
    }
}
