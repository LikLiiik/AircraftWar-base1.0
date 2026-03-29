package edu.hitsz.aircraft;

import edu.hitsz.application.Main;

/**
 * 敌机抽象父类
 * 所有敌机的统一基类
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft {

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 敌机基础移动逻辑
     */
    @Override
    public void forward() {
        super.forward();
        // 向下出界判定
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
