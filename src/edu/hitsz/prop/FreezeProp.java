package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.SoundManager;

/**
 * 冰冻道具：冻结全场敌机（观察者模式）
 */
public class FreezeProp extends AbstractProp {

    // 冰冻持续时间（毫秒）
    private static final int FREEZE_DURATION = 5000;

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.FREEZE_PROP_IMAGE;
        this.width = ImageManager.FREEZE_PROP_IMAGE.getWidth();
        this.height = ImageManager.FREEZE_PROP_IMAGE.getHeight();
    }

    @Override
    public void activate(HeroAircraft hero) {
        SoundManager.getInstance().playGetSupply();
        System.out.println("FreezeProp active! 全场敌机被冻结 " + FREEZE_DURATION + "ms");
        
        // 通知 Game 触发冰冻事件
        if (hero.getGame() != null) {
            hero.getGame().triggerFreezeEffect(FREEZE_DURATION);
        }
    }
}
