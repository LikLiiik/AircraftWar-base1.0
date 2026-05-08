package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.EnemyFanShotStrategy;
import java.util.List;
import java.util.Random;

/**
 * 王牌敌机
 * 炸弹效果：掉血（不坠毁）
 * 冰冻效果：减速 5s 后恢复
 */
public class AceEnemy extends AbstractEnemyAircraft {

    // ===================== 移动配置 =====================
    // 横向移动速度
    private int moveSpeedX = 3;
    // 移动方向计数器
    private int moveCounter = 0;
    // 移动周期
    private static final int MOVE_CYCLE = 50;
    
    // 减速状态
    private boolean slowed = false;
    private long slowEndTime = 0;
    private int originalSpeedX = 0;

    // ===================== 道具掉落配置 =====================
    // 随机数工具
    private final Random random = new Random();
    // 道具总掉落概率 (50%)
    private static final int PROP_DROP_RATE = 5;
    // 道具向下飞行速度
    private static final int PROP_SPEED_Y = 5;

    // 射击策略
    private ShootStrategy shootStrategy;

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.power = 20;
        this.direction = 1;
        this.shootStrategy = new EnemyFanShotStrategy();
        // 初始化时设置一个随机的横向移动方向
        int initialDirection = (Math.random() < 0.5) ? -1 : 1;
        this.speedX = initialDirection * moveSpeedX;
        this.originalSpeedX = this.speedX;
    }

    @Override
    public void forward() {
        // 如果被冰冻/减速，降低速度
        if (frozen || slowed) {
            if (System.currentTimeMillis() >= freezeEndTime) {
                frozen = false;
                slowed = false;
                this.speedX = originalSpeedX;
            } else {
                // 减速移动
                super.forward();
                if (locationY >= Main.WINDOW_HEIGHT) {
                    vanish();
                }
                return;
            }
        }
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
            this.originalSpeedX = this.speedX;
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
        return shootStrategy.shoot(this);
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
    
    /**
     * 王牌敌机：炸弹只掉血不坠毁
     */
    @Override
    protected void onBombHit() {
        this.hp = Math.max(0, this.hp - 50); // 扣50血
        if (this.hp <= 0) {
            vanish();
        }
    }
    
    /**
     * 王牌敌机：冰冻后减速 5s
     */
    @Override
    protected void onFreeze(int duration) {
        this.frozen = true;
        this.slowed = true;
        this.freezeEndTime = System.currentTimeMillis() + 5000; // 5秒
        this.speedX = this.speedX / 2; // 速度减半
    }
}
