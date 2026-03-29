package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 超级火力道具
 */
public class SuperFireProp extends AbstractProp {

    public SuperFireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft hero) {
        // 按要求打印日志
        System.out.println("FirePlusSupply active!");
    }
}