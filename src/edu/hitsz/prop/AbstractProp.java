package edu.hitsz.aircraft;

import edu.hitsz.basic.AbstractFlyingObject;

/**
 * 道具抽象父类
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    // 道具统一移动逻辑（空实现）
    @Override
    public void forward() {
        // 道具向下移动
    }
}