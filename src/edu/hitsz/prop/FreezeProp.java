package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

/**
 * 冰冻道具：冻结全场敌机
 */
public class FreezeProp extends AbstractProp {

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.FREEZE_PROP_IMAGE;
        this.width = ImageManager.FREEZE_PROP_IMAGE.getWidth();
        this.height = ImageManager.FREEZE_PROP_IMAGE.getHeight();
    }

    @Override
    public void activate(HeroAircraft hero) {
        System.out.println("FreezeProp active! 全场敌机被冻结");
    }
}
