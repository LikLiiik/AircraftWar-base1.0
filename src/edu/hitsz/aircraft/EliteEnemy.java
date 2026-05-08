package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.FireProp;
import edu.hitsz.prop.SuperFireProp;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.EnemySingleRowStrategy;

import java.util.List;
import java.util.Random;

/**
 * 精英敌机
 * 功能：按周期向下直射单排子弹 + 坠毁概率掉落指定道具
 * 炸弹效果：坠毁
 * 冰冻效果：静止 4s 后恢复
 * @author hitsz
 */
public class EliteEnemy extends AbstractEnemyAircraft {

    // ===================== 道具掉落配置 =====================
    // 随机数工具
    private final Random random = new Random();
    // 道具总掉落概率 (30%)
    private static final int PROP_DROP_RATE = 3;
    // 道具向下飞行速度
    private static final int PROP_SPEED_Y = 5;

    // 射击策略
    private ShootStrategy shootStrategy;

    // =======================================================

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.power = 5;
        this.direction = 1;
        this.shootStrategy = new EnemySingleRowStrategy();
    }

    /**
     * 发射单排直射子弹
     */
    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this);
    }

    /**
     * 【核心方法】敌机坠毁时，随机生成道具
     * @return 生成的道具对象，无道具则返回null
     */
    public AbstractProp createProp() {
        // 判定是否掉落道具
        if (random.nextInt(10) >= PROP_DROP_RATE) {
            return null;
        }

        // 随机生成 0/1/2，对应三种道具
        int propType = random.nextInt(3);
        int x = this.getLocationX();
        int y = this.getLocationY();

        return switch (propType) {
            // 0: 加血道具
            case 0 -> new BloodProp(x, y, 0, PROP_SPEED_Y);
            // 1: 火力道具
            case 1 -> new FireProp(x, y, 0, PROP_SPEED_Y);
            // 2: 超级火力道具
            case 2 -> new SuperFireProp(x, y, 0, PROP_SPEED_Y);
            default -> null;
        };
    }
    
    /**
     * 精英敌机：冰冻后静止 4s
     */
    @Override
    protected void onFreeze(int duration) {
        this.frozen = true;
        this.freezeEndTime = System.currentTimeMillis() + 4000; // 4秒
    }
}
