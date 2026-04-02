package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 精锐敌机
 */
public class ElitePlusEnemy extends AbstractEnemyAircraft {

    // ===================== 移动配置 =====================
    // 横向移动速度
    private int moveSpeedX = 2;
    // 移动方向计数器
    private int moveCounter = 0;
    // 移动周期
    private static final int MOVE_CYCLE = 50;

    // ===================== 射击配置参数 =====================
    // 每次射击发射 2 发子弹（双排直射）
    private int shootNum = 2;
    // 子弹威力
    private int power = 30;
    // 子弹射击方向：敌机向下发射 = 1
    private int direction = 1;
    // 子弹横向间距
    private static final int BULLET_SPACING = 20;

    // ===================== 道具掉落配置 =====================
    // 随机数工具
    private final Random random = new Random();
    // 道具总掉落概率 (30%)
    private static final int PROP_DROP_RATE = 3;
    // 道具向下飞行速度
    private static final int PROP_SPEED_Y = 5;

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        // 初始化时设置一个随机的横向移动方向
        int initialDirection = (Math.random() < 0.5) ? -1 : 1;
        this.speedX = initialDirection * moveSpeedX;
    }

    @Override
    public void forward() {
        super.forward();
        
        // 边界修正：防止超出屏幕
        if (locationX < 0) {
            locationX = 0;
        } else if (locationX > Main.WINDOW_WIDTH) {
            locationX = Main.WINDOW_WIDTH;
        }
        
        // 每隔一定周期改变横向移动方向
        moveCounter++;
        if (moveCounter >= MOVE_CYCLE) {
            moveCounter = 0;
            // 随机决定横向移动方向：-1 左，1 右
            int newDirection = (Math.random() < 0.5) ? -1 : 1;
            this.speedX = newDirection * moveSpeedX;
        }
        
        // 向下飞行出界判定
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    /**
     * 发射双排直射子弹
     */
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        
        // 双排子弹：左排和右排
        for (int i = -1; i <= 1; i += 2) {
            int bulletX = x + i * BULLET_SPACING;
            int speedX = 0;
            int speedY = this.getSpeedY() + direction * 5;
            
            BaseBullet bullet = new EnemyBullet(bulletX, y, speedX, speedY, power);
            res.add(bullet);
        }
        
        return res;
    }

    /**
     * 【核心方法】敌机坠毁时，随机生成道具（4种：不含冰冻）
     * @return 生成的道具对象，无道具则返回null
     */
    public AbstractProp createProp() {
        // 判定是否掉落道具
        if (random.nextInt(10) >= PROP_DROP_RATE) {
            return null;
        }

        // 随机生成 0/1/2/3，对应四种道具（不含冰冻）
        int propType = random.nextInt(4);
        int x = this.getLocationX();
        int y = this.getLocationY();

        return switch (propType) {
            // 0: 加血道具
            case 0 -> new BloodProp(x, y, 0, PROP_SPEED_Y);
            // 1: 火力道具
            case 1 -> new FireProp(x, y, 0, PROP_SPEED_Y);
            // 2: 超级火力道具
            case 2 -> new SuperFireProp(x, y, 0, PROP_SPEED_Y);
            // 3: 炸弹道具
            case 3 -> new BombProp(x, y, 0, PROP_SPEED_Y);
            default -> null;
        };
    }
}
