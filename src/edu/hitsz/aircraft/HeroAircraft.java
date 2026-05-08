package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.application.ImageManager;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.SingleShotStrategy;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    // 【新增】英雄机最大血量（固定，加血不会超过这个值）
    public static final int MAX_HP = 100;

    //射击策略
    private ShootStrategy shootStrategy;

    private static volatile HeroAircraft instance;
    
    // 关联的 Game 实例
    private Game game;

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.power = 50;
        this.direction = -1;
        this.shootStrategy = new SingleShotStrategy();
    }

    public static HeroAircraft getInstance() {
        if(instance == null){
            synchronized(HeroAircraft.class){
                if(instance == null){
                    instance = new HeroAircraft(
                            Main.WINDOW_WIDTH/2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0,0,100
                    );
                }
            }
        }
        return instance;
    }
    
    /**
     * 设置关联的 Game 实例
     */
    public void setGame(Game game) {
        this.game = game;
    }
    
    /**
     * 获取关联的 Game 实例
     */
    public Game getGame() {
        return game;
    }


    // 【新增】加血方法（不超过最大值）
    public void addHp(int add) {
        int newHp = this.hp + add;
        this.hp = Math.min(newHp, MAX_HP);
    }

    // 设置射击策略
    public void setShootStrategy(ShootStrategy strategy) {
        this.shootStrategy = strategy;
    }

    // 恢复基础射击策略
    public void resetShootStrategy() {
        this.shootStrategy = new SingleShotStrategy();
    }
    
    // 重置英雄机状态（用于新游戏）
    public void reset() {
        this.hp = MAX_HP;
        this.setLocation(
            Main.WINDOW_WIDTH/2,
            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight()
        );
        this.shootStrategy = new SingleShotStrategy();
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this);
    }

}
