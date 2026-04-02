package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.FireProp;
import edu.hitsz.prop.SuperFireProp;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 精英敌机
 * 功能：按周期向下直射单排子弹 + 坠毁概率掉落指定道具
 * @author hitsz
 */
public class EliteEnemy extends AbstractEnemyAircraft {

    // ===================== 射击配置参数 =====================
    // 每次射击发射 1 发子弹（单排直射）
    private int shootNum = 1;
    // 子弹威力
    private int power = 30;
    // 子弹射击方向：敌机向下发射 = 1
    private int direction = 1;

    // ===================== 道具掉落配置 =====================
    // 随机数工具
    private final Random random = new Random();
    // 道具总掉落概率 (30%)
    private static final int PROP_DROP_RATE = 3;
    // 道具向下飞行速度
    private static final int PROP_SPEED_Y = 5;

    // =======================================================

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 发射单排直射子弹
     */
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;

        BaseBullet bullet = new EnemyBullet(x, y, speedX, speedY, power);
        res.add(bullet);
        return res;
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
}
