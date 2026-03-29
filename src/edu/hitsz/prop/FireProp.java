package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 火力道具
 */
public class FireProp extends AbstractProp {

    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft hero) {
        // 按要求打印日志
        System.out.println("FireSupply active!");
    }
}
