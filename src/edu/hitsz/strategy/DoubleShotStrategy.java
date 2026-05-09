package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 双发直射策略
 * 天赋解锁的射击方式
 */
public class DoubleShotStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection() * 5;
        int power = aircraft.getPower();

        // 左子弹
        BaseBullet bullet1 = new HeroBullet(x - 15, y, 0, speedY, power);
        res.add(bullet1);

        // 右子弹
        BaseBullet bullet2 = new HeroBullet(x + 15, y, 0, speedY, power);
        res.add(bullet2);

        return res;
    }
}
