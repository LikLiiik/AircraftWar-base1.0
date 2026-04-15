package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.FireProp;
import edu.hitsz.prop.FreezeProp;
import edu.hitsz.prop.SuperFireProp;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.BossRingShotStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Boss敌机
 * 功能：悬浮于界面上方左右移动，环射弹道（20发子弹），掉落3个道具
 */
public class BossEnemy extends AbstractEnemyAircraft {

    // ===================== 移动配置 =====================
    // 横向移动速度
    private int moveSpeedX = 2;
    // 移动方向计数器
    private int moveCounter = 0;
    // 移动周期
    private static final int MOVE_CYCLE = 30;

    // ===================== 道具掉落配置 =====================
    // 随机数工具
    private final Random random = new Random();
    // 道具向下飞行速度
    private static final int PROP_SPEED_Y = 5;

    // 射击策略
    private ShootStrategy shootStrategy;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.power = 30;
        this.direction = 1;
        this.shootStrategy = new BossRingShotStrategy();
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
            speedX = Math.abs(speedX);
        } else if (locationX > Main.WINDOW_WIDTH - width) {
            locationX = Main.WINDOW_WIDTH - width;
            speedX = -Math.abs(speedX);
        }
        
        // 每隔一定周期改变横向移动方向
        moveCounter++;
        if (moveCounter >= MOVE_CYCLE) {
            moveCounter = 0;
            // 随机决定横向移动方向：-1 左，1 右
            int newDirection = (Math.random() < 0.5) ? -1 : 1;
            this.speedX = newDirection * moveSpeedX;
        }
        
        // Boss 敌机不向下移动，只在上方区域
        // 如果意外移出上边界，将其拉回
        if (locationY < 0) {
            locationY = 0;
        }
    }

    /**
     * 发射环形散射子弹（20发）
     */
    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this);
    }

    /**
     * 【核心方法】Boss 敌机坠毁时，随机生成 3 个道具
     * @return 生成的道具列表
     */
    public List<AbstractProp> createProp() {
        List<AbstractProp> props = new LinkedList<>();
        int x = this.getLocationX() + this.width / 2;
        int y = this.getLocationY() + this.height / 2;
        
        // 生成 3 个道具
        for (int i = 0; i < 3; i++) {
            // 随机生成 0/1/2/3/4，对应五种道具
            int propType = random.nextInt(5);
            props.add(createPropByType(propType, x, y));
        }
        
        return props;
    }
    
    /**
     * 根据类型创建道具
     */
    private AbstractProp createPropByType(int propType, int x, int y) {
        return switch (propType) {
            case 0 -> new BloodProp(x, y, 0, PROP_SPEED_Y);
            case 1 -> new FireProp(x, y, 0, PROP_SPEED_Y);
            case 2 -> new SuperFireProp(x, y, 0, PROP_SPEED_Y);
            case 3 -> new FreezeProp(x, y, 0, PROP_SPEED_Y);
            case 4 -> new BombProp(x, y, 0, PROP_SPEED_Y);
            default -> null;
        };
    }
}
