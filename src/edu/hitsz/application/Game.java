package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dao.Leaderboard;
import edu.hitsz.factory.*;
import edu.hitsz.observer.Observer;
import edu.hitsz.prop.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动（模板模式基类 + 观察者模式被观察者）
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器，用于定时任务调度
    private final Timer timer;
    //时间间隔 (ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    protected final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    // 难度配置
    protected DifficultyConfig config;

    //屏幕中出现的敌机最大数量
    protected int enemyMaxNumber;

    //敌机生成周期
    protected double enemySpawnCycle;
    protected int enemySpawnCounter = 0;

    //英雄机和敌机射击周期
    protected double heroShootCycle;
    protected double enemyShootCycle;
    private int heroShootCounter = 0;
    private int enemyShootCounter = 0;

    //当前玩家分数
    protected int score = 0;

    //游戏结束标志
    private boolean gameOverFlag = false;

    // 排行榜
    private Leaderboard leaderboard;
    
    // 当前难度
    private String difficulty;

    // Boss 生成阈值
    protected int bossScoreThreshold;
    protected int bossSpawnCounter = 0;
    
    // 观察者列表
    private List<Observer> observers = new ArrayList<>();

    // 游戏时间计数（用于难度递增）
    protected int gameTimeCounter = 0;
    // 上一次难度提升的时间点
    protected int lastDifficultyIncreaseTime = 0;
    // 难度提升间隔（帧数，约30秒 = 30*1000/40 = 750帧）
    protected static final int DIFFICULTY_INCREASE_INTERVAL = 750;

    // 当前玩家名称（用于天赋系统）
    protected String currentPlayerName = null;

    public Game(String difficulty) {
        this.difficulty = difficulty;
        heroAircraft = HeroAircraft.getInstance();
        
        // 设置 Game 引用
        heroAircraft.setGame(this);
        
        // 重置英雄机状态（HP、位置、射击策略）
        heroAircraft.reset();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);
        
        // 初始化排行榜
        this.leaderboard = new Leaderboard(difficulty);
        
        // 加载对应难度的背景图片
        loadBackgroundImage(difficulty);
        
        // 注册敌机为观察者
        registerEnemyObservers();

    }

    /**
     * 设置当前玩家名称并应用天赋
     */
    public void setCurrentPlayer(String playerName) {
        this.currentPlayerName = playerName;
        applyPlayerTalents();
    }

    /**
     * 应用玩家天赋效果
     */
    // 天赋效果值
    protected int speedBoostLevel = 0;
    protected int defenseBoostLevel = 0;
    protected int luckBoostLevel = 0;
    protected int rebirthLevel = 0;
    protected boolean hasRebirthUsed = false;

    protected void applyPlayerTalents() {
        if (currentPlayerName == null) return;
        
        edu.hitsz.talent.PlayerTalent playerTalent = 
            edu.hitsz.talent.TalentSystem.getInstance().loadPlayer(currentPlayerName);
        
        int lifeBoost = playerTalent.getTalentLevel("life_boost");
        int fireBoost = playerTalent.getTalentLevel("fire_boost");
        int dualFire = playerTalent.getTalentLevel("dual_fire");
        speedBoostLevel = playerTalent.getTalentLevel("speed_boost");
        defenseBoostLevel = playerTalent.getTalentLevel("defense_boost");
        luckBoostLevel = playerTalent.getTalentLevel("luck_boost");
        rebirthLevel = playerTalent.getTalentLevel("rebirth");
        
        heroAircraft.applyTalents(lifeBoost, fireBoost, dualFire);
        
        // 应用极速射击：每级减少1%射击周期
        double speedReduction = speedBoostLevel * 0.01;
        heroShootCycle = (int) Math.round(heroShootCycle * (1 - speedReduction));
        if (heroShootCycle < 5) heroShootCycle = 5; // 最小射击周期
        
        System.out.println("[天赋] 玩家：" + currentPlayerName + 
            " 生命+" + (lifeBoost * 5) + 
            " 火力+" + (fireBoost * 5) + 
            " 双发=" + (dualFire > 0) +
            " 极速=" + speedBoostLevel + "%" +
            " 防御=" + defenseBoostLevel + "%" +
            " 幸运=" + luckBoostLevel + "%" +
            " 复活=" + (rebirthLevel * 2) + "%");
    }
    
    /**
     * 注册所有敌机为观察者
     */
    protected void registerEnemyObservers() {
        // 在敌机生成时注册
    }
    
    /**
     * 注册单个敌机为观察者
     */
    protected void registerEnemyObserver(AbstractEnemyAircraft enemy) {
        addObserver(enemy);
    }
    
    /**
     * 添加观察者
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    /**
     * 通知所有观察者
     */
    public void notifyObservers(String event, int duration) {
        for (Observer observer : observers) {
            observer.onNotify(event, duration);
        }
    }
    
    /**
     * 触发炸弹效果（观察者模式）
     */
    public void triggerBombEffect() {
        System.out.println("[Game] 炸弹效果触发 - 清屏");
        // 通知所有敌机
        notifyObservers("bomb", 0);
        // 播放音效
        SoundManager.getInstance().playBombExplosion();
    }
    
    /**
     * 触发冰冻效果（观察者模式）
     */
    public void triggerFreezeEffect(int duration) {
        System.out.println("[Game] 冰冻效果触发 - 冻结 " + duration + "ms");
        // 通知所有敌机
        notifyObservers("freeze", duration);
    }
    
    /**
     * 加载对应难度的背景图片
     */
    private void loadBackgroundImage(String difficulty) {
        try {
            String bgPath = DifficultySelection.getBackgroundImage(difficulty);
            ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream(bgPath));
        } catch (IOException e) {
            System.err.println("背景图片加载失败：" + e.getMessage());
        }
    }

    /**
     * 游戏启动入口，执行游戏逻辑（模板方法）
     */
    public void action() {
        
        // 播放背景音乐
        SoundManager.getInstance().playBGM();
        
        // 加载难度配置
        loadDifficulty();
        
        // 应用初始配置
        applyDifficultyConfig();

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                
                // 游戏时间递增
                gameTimeCounter++;
                
                // 检查是否需要提升难度
                if (config.difficultyIncreaseOverTime) {
                    checkAndIncreaseDifficulty();
                }

                // 检查是否达到 Boss 生成条件
                if (canSpawnBoss() && score >= (bossSpawnCounter + 1) * bossScoreThreshold) {
                    spawnBoss();
                    bossSpawnCounter++;
                }
                
                enemySpawnCounter++;
                if (enemySpawnCounter >= enemySpawnCycle) {
                    enemySpawnCounter = 0;
                    spawnEnemy();
                }

                // 飞机移动
                aircraftsMoveAction();
                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);

    }
    
    /**
     * 应用难度配置到游戏参数
     */
    protected void applyDifficultyConfig() {
        this.enemyMaxNumber = config.enemyMaxNumber;
        this.enemySpawnCycle = config.enemySpawnCycle;
        this.heroShootCycle = config.heroShootCycle;
        this.enemyShootCycle = config.enemyShootCycle;
        this.bossScoreThreshold = config.bossScoreThreshold;
    }
    
    /**
     * 检查并提升游戏难度
     */
    protected void checkAndIncreaseDifficulty() {
        if (gameTimeCounter - lastDifficultyIncreaseTime >= DIFFICULTY_INCREASE_INTERVAL) {
            lastDifficultyIncreaseTime = gameTimeCounter;
            increaseDifficulty();
        }
    }
    
    /**
     * 提升游戏难度（由子类实现具体逻辑）
     */
    protected abstract void increaseDifficulty();
    
    //***********************
    //      模板方法钩子
    //***********************
    
    /**
     * 加载难度配置（由子类实现）
     */
    protected abstract void loadDifficulty();
    
    /**
     * 获取敌机生成周期（由子类实现）
     */
    protected abstract double getEnemySpawnCycle();
    
    /**
     * 是否可以生成 Boss（由子类实现）
     */
    protected abstract boolean canSpawnBoss();
    
    /**
     * 获取 Boss 血量（由子类实现）
     */
    protected abstract int getBossHp();
    
    /**
     * 生成敌机（由子类实现差异化）
     */
    protected abstract void spawnEnemy();

    /**
     * 生成 Boss 敌机
     */
    protected void spawnBoss() {
        int bossHp = getBossHp();
        int bossX = Main.WINDOW_WIDTH / 2 - ImageManager.BOSS_ENEMY_IMAGE.getWidth() / 2;
        int bossY = 50;
        AbstractEnemyAircraft boss = EnemyFactoryManager.createEnemy(
                4, // TYPE_BOSS
                bossX,
                bossY,
                0,
                0,
                bossHp
        );
        enemyAircrafts.add(boss);
        // 注册 Boss 为观察者
        addObserver(boss);
        System.out.println("Boss spawned! Score: " + score + ", HP: " + bossHp);
        
        // Boss 出场时播放专属背景音乐
        SoundManager.getInstance().playBossBGM();
    }

    //***********************
    //      Action 各部分
    //***********************

    private void shootAction() {
        // 英雄机射击
        heroShootCounter++;
        if (heroShootCounter >= heroShootCycle) {
            heroShootCounter = 0;
            heroBullets.addAll(heroAircraft.shoot());
        }
        
        // 敌机射击
        enemyShootCounter++;
        if (enemyShootCounter >= enemyShootCycle) {
            enemyShootCounter = 0;
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft instanceof AbstractEnemyAircraft) {
                    List<BaseBullet> bullets = enemyAircraft.shoot();
                    // 将敌机子弹注册为观察者，使其受炸弹和冰冻影响
                    for (BaseBullet bullet : bullets) {
                        if (bullet instanceof Observer) {
                            addObserver((Observer) bullet);
                        }
                    }
                    enemyBullets.addAll(bullets);
                }
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄机
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 钢铁之躯：每级减免1%伤害
                double damageReduction = defenseBoostLevel * 0.01;
                int damage = (int) Math.round(bullet.getPower() * (1 - damageReduction));
                if (damage < 1) damage = 1; // 最少造成1点伤害
                heroAircraft.decreaseHp(damage);
                SoundManager.getInstance().playBulletHit();
                bullet.vanish();
            }
        }

        // 英雄机与道具碰撞
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                prop.activate(heroAircraft);
                prop.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    SoundManager.getInstance().playBulletHit();
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 敌机坠毁时生成道具
                        if (enemyAircraft instanceof ElitePlusEnemy) {
                            AbstractProp prop = ((ElitePlusEnemy) enemyAircraft).createProp();
                            if (prop != null) {
                                props.add(prop);
                            } else if (luckBoostLevel > 0 && Math.random() * 100 < luckBoostLevel) {
                                // 幸运之星：未掉落时额外概率掉落
                                prop = ((ElitePlusEnemy) enemyAircraft).createProp();
                                if (prop != null) props.add(prop);
                            }
                        } else if (enemyAircraft instanceof AceEnemy) {
                            AbstractProp prop = ((AceEnemy) enemyAircraft).createProp();
                            if (prop != null) {
                                props.add(prop);
                            } else if (luckBoostLevel > 0 && Math.random() * 100 < luckBoostLevel) {
                                prop = ((AceEnemy) enemyAircraft).createProp();
                                if (prop != null) props.add(prop);
                            }
                        } else if (enemyAircraft instanceof BossEnemy) {
                            // Boss 敌机掉落 3 个道具
                            List<AbstractProp> bossProps = ((BossEnemy) enemyAircraft).createProp();
                            props.addAll(bossProps);
                            
                            // Boss 被击毁，停止 Boss BGM，恢复普通 BGM
                            SoundManager.getInstance().playBGM();
                        }
                        score += 10;
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                    SoundManager.getInstance().playBombExplosion();
                }
            }
        }

        // Todo: 我方获得道具，道具生效

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     * 4. 清理已失效的观察者
     */
    private void postProcessAction() {
        // 清理已失效子弹的观察者注册
        enemyBullets.removeIf(bullet -> {
            if (bullet.notValid() && bullet instanceof Observer) {
                observers.remove((Observer) bullet);
                return true;
            }
            return bullet.notValid();
        });
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            // 不死鸟天赋：死亡时概率复活
            if (!hasRebirthUsed && rebirthLevel > 0) {
                int rebirthChance = rebirthLevel * 2; // 每级2%概率
                if (Math.random() * 100 < rebirthChance) {
                    heroAircraft.setHp(heroAircraft.getHp() + 50); // 复活恢复50HP
                    hasRebirthUsed = true;
                    System.out.println("[不死鸟] 天赋触发！复活成功！HP+50");
                    JOptionPane.showMessageDialog(
                        Main.cardPanel,
                        "不死鸟天赋触发！复活成功！",
                        "天赋效果",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    return; // 不结束游戏
                }
            }
            
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            System.out.println("Game Over!");
            
            // 播放游戏结束音效
            SoundManager.getInstance().playGameOver();
            
            // 保存分数（使用游戏开始时的玩家名）
            SwingUtilities.invokeLater(() -> {
                String playerName = (currentPlayerName != null && !currentPlayerName.isEmpty()) 
                    ? currentPlayerName : "游客";
                
                // 保存到排行榜
                leaderboard.addRecord(score, playerName, difficulty);
                // 保存到天赋系统（累计得分只增不减）
                edu.hitsz.talent.TalentSystem.getInstance().addScore(playerName, score);
                
                JOptionPane.showMessageDialog(
                    Main.cardPanel,
                    "游戏结束！\n玩家：" + playerName + "\n得分：" + score + "\n\n得分已保存到排行榜和天赋系统！",
                    "游戏结束",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                // 显示排行榜界面
                LeaderboardUI leaderboardUI = new LeaderboardUI(score, difficulty);
                Main.cardPanel.add(leaderboardUI.getMainPanel(), "leaderboard");
                Main.cardLayout.show(Main.cardPanel, "leaderboard");
            });
        }
    };

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机，最后绘制道具
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, x, y);
        g.drawString("Life: " + heroAircraft.getHp(), x, y + 20);
    }
}
