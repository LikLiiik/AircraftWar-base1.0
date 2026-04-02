package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 王牌敌机
 */
public class AceEnemy extends AbstractEnemyAircraft {

    // ===================== 移动配置 =====================
    // 横向移动速度
    private int moveSpeedX = 3;
    // 移动方向计数器
    private int moveCounter = 0;
    // 移动周期
    private static final int MOVE_CYCLE = 50;

    // ===================== 射击配置参数 =====================
    // 每次射击发射 3 发子弹（扇形散射）
    private int shootNum = 3;
    // 子弹威力
    private int power = 30;
    // 子弹射击方向：敌机向下发射 = 1
    private int direction = 1;
    // 扇形散射角度间隔（度）
    private static final int ANGLE_SPACING = 15;

    // ===================== 道具掉落配置 =====================
    // 随机数工具
    private final Random random = new Random();
    // 道具总掉落概率 (50%)
    private static final int PROP_DROP_RATE = 5;
    // 道具向下飞行速度
    private static final int PROP_SPEED_Y = 5;

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
     * 发射扇形散射子弹（3发）
     */
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        
        // 中间子弹（垂直向下）
        res.add(new EnemyBullet(x, y, 0, this.getSpeedY() + direction * 5, power));
        
        // 左侧子弹（向左下方）
        double angleLeft = Math.toRadians(ANGLE_SPACING);
        double speedXLeft = -Math.sin(angleLeft) * 5;
        double speedYLeft = Math.cos(angleLeft) * 5;
        res.add(new EnemyBullet(x, y, (int)speedXLeft, (int)(speedYLeft + direction * 5), power));
        
        // 右侧子弹（向右下方）
        double angleRight = Math.toRadians(ANGLE_SPACING);
        double speedXRight = Math.sin(angleRight) * 5;
        double speedYRight = Math.cos(angleRight) * 5;
        res.add(new EnemyBullet(x, y, (int)speedXRight, (int)(speedYRight + direction * 5), power));
        
        return res;
    }

    /**
     * 【核心方法】敌机坠毁时，随机生成道具（全部5种）
     * @return 生成的道具对象，无道具则返回null
     */
    public AbstractProp createProp() {
        // 判定是否掉落道具
        if (random.nextInt(10) >= PROP_DROP_RATE) {
            return null;
        }

        // 随机生成 0/1/2/3/4，对应五种道具
        int propType = random.nextInt(5);
        int x = this.getLocationX();
        int y = this.getLocationY();

        return switch (propType) {
            // 0: 加血道具
            case 0 -> new BloodProp(x, y, 0, PROP_SPEED_Y);
            // 1: 火力道具
            case 1 -> new FireProp(x, y, 0, PROP_SPEED_Y);
            // 2: 超级火力道具
            case 2 -> new SuperFireProp(x, y, 0, PROP_SPEED_Y);
            // 3: 冰冻道具
            case 3 -> new FreezeProp(x, y, 0, PROP_SPEED_Y);
            // 4: 炸弹道具
            case 4 -> new BombProp(x, y, 0, PROP_SPEED_Y);
            default -> null;
        };
    }
}
