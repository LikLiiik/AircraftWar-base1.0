package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

/**
 * 火力道具
 */
public class FireProp extends AbstractProp {

    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.FIRE_PROP_IMAGE;
        this.width = ImageManager.FIRE_PROP_IMAGE.getWidth();
        this.height = ImageManager.FIRE_PROP_IMAGE.getHeight();
    }

    @Override
    public void activate(HeroAircraft hero) {
        // 按要求打印日志
        System.out.println("FireSupply active!");
    }
}
