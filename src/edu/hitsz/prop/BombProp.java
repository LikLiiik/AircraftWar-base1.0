package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;

/**
 * 炸弹道具：触发清屏效果
 */
public class BombProp extends AbstractProp {

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.BOMB_PROP_IMAGE;
        this.width = ImageManager.BOMB_PROP_IMAGE.getWidth();
        this.height = ImageManager.BOMB_PROP_IMAGE.getHeight();
    }

    @Override
    public void activate(HeroAircraft hero) {
        System.out.println("BombProp active! 清屏炸弹触发");
    }
}
