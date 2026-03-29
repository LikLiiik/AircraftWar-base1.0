package edu.hitsz.prop;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.application.Main;
import edu.hitsz.aircraft.HeroAircraft;

/**
 * 道具抽象父类
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    // 【新增】道具生效抽象方法（英雄机拾取后调用）
    public abstract void activate(HeroAircraft hero);

    // 道具统一移动逻辑（空实现）
    @Override
    public void forward() {
        // 道具向下移动
        super.forward();
        // 向下飞出界面后销毁
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}