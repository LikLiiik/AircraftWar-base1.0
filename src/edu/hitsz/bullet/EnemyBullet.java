package edu.hitsz.bullet;

import edu.hitsz.observer.Observer;

/**
 * 敌机子弹
 * 炸弹效果：消失
 * 冰冻效果：静止 5s 后恢复
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Observer {

    // 冰冻状态
    private boolean frozen = false;
    private long freezeEndTime = 0;
    // 保存原始速度用于恢复
    private int originalSpeedX = 0;
    private int originalSpeedY = 0;

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
        this.originalSpeedX = speedX;
        this.originalSpeedY = speedY;
    }

    @Override
    public void forward() {
        // 如果被冰冻，检查是否到期
        if (frozen) {
            if (System.currentTimeMillis() >= freezeEndTime) {
                frozen = false;
                // 恢复原始速度
                this.speedX = originalSpeedX;
                this.speedY = originalSpeedY;
            } else {
                // 静止不动
                return;
            }
        }
        super.forward();
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
     * 炸弹效果：子弹消失
     */
    private void onBombHit() {
        this.vanish();
    }

    /**
     * 冰冻效果：子弹静止 5s
     */
    private void onFreeze(int duration) {
        this.frozen = true;
        this.freezeEndTime = System.currentTimeMillis() + 5000; // 5秒
        // 保存当前速度（如果还没保存过）
        if (!frozen) {
            this.originalSpeedX = this.speedX;
            this.originalSpeedY = this.speedY;
        }
        // 停止移动
        this.speedX = 0;
        this.speedY = 0;
    }
}
