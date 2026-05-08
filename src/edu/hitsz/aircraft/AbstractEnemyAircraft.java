package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.observer.Observer;

/**
 * 敌机抽象父类
 * 所有敌机的统一基类
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft implements Observer {

    // 冰冻状态
    protected boolean frozen = false;
    protected long freezeEndTime = 0;

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 敌机基础移动逻辑
     */
    @Override
    public void forward() {
        // 如果被冰冻，不移动
        if (frozen) {
            if (System.currentTimeMillis() >= freezeEndTime) {
                frozen = false;
            } else {
                return;
            }
        }
        super.forward();
        // 向下出界判定
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    /**
     * 观察者接口实现 - 接收炸弹、冰冻事件通知
     */
    @Override
    public void onNotify(String event, int duration) {
        if ("bomb".equals(event)) {
            onBombHit();
        } else if ("freeze".equals(event)) {
            onFreeze(duration);
        }
    }

    /**
     * 炸弹命中效果 - 由子类重写实现差异化
     */
    protected void onBombHit() {
        this.hp = 0;
        vanish();
    }

    /**
     * 冰冻效果 - 由子类重写实现差异化
     */
    protected void onFreeze(int duration) {
        this.frozen = true;
        this.freezeEndTime = System.currentTimeMillis() + duration;
    }
}
