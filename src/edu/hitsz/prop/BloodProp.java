package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

/**
 * 加血道具：恢复30点血量
 */
public class BloodProp extends AbstractProp {

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.BLOOD_PROP_IMAGE;
        this.width = ImageManager.BLOOD_PROP_IMAGE.getWidth();
        this.height = ImageManager.BLOOD_PROP_IMAGE.getHeight();
    }

    @Override
    public void activate(HeroAircraft hero) {
        hero.addHp(30);
        System.out.println("BloodProp active! 生命值恢复");
    }
}
