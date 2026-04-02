package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

/**
 * 超级火力道具
 */
public class SuperFireProp extends AbstractProp {

    public SuperFireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.SUPER_FIRE_PROP_IMAGE;
        this.width = ImageManager.SUPER_FIRE_PROP_IMAGE.getWidth();
        this.height = ImageManager.SUPER_FIRE_PROP_IMAGE.getHeight();
    }

    @Override
    public void activate(HeroAircraft hero) {
        // 按要求打印日志
        System.out.println("FirePlusSupply active!");
    }
}
