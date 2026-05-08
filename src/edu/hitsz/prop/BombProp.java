package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.SoundManager;

/**
 * 炸弹道具：触发清屏效果（观察者模式）
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
        SoundManager.getInstance().playBombExplosion();
        System.out.println("BombProp active! 清屏炸弹触发");
        
        // 通知 Game 触发炸弹事件
        if (hero.getGame() != null) {
            hero.getGame().triggerBombEffect();
        }
    }
}
