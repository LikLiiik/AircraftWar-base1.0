package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.factory.EnemyFactoryManager;

/**
 * 困难难度游戏
 * 特点：
 * - 可以生成Boss，每次召唤Boss血量递增
 * - 敌机生成周期短，敌机血量高，速度快
 * - 英雄机射击周期长（射速慢）
 * - 敌机射击周期短（射速快）
 * - 随游戏时间逐步提升：敌机产生周期、敌机速度、敌机血量、英雄机射击周期、敌机射击周期
 * - 生成所有类型敌机（不含Boss）
 * @author AircraftWar
 */
public class HardGame extends Game {

    // 当前敌机属性倍率（随时间递增）
    private double currentHpMultiplier = 1.0;
    private double currentSpeedMultiplier = 1.0;
    private double currentSpawnCycleMultiplier = 1.0;
    // 射击周期倍率
    private double currentHeroShootCycleMultiplier = 1.0;
    private double currentEnemyShootCycleMultiplier = 1.0;

    public HardGame() {
        super("hard");
    }

    @Override
    protected void loadDifficulty() {
        System.out.println("[HardGame] 加载困难难度配置");
        this.config = DifficultyConfig.getHardConfig();
    }

    @Override
    protected void increaseDifficulty() {
        // 困难难度：敌机产生周期、敌机速度、敌机血量、英雄机射击周期、敌机射击周期都随时间变化
        currentSpawnCycleMultiplier *= (1 - config.spawnCycleDecreaseRate);
        currentSpeedMultiplier *= (1 + config.speedIncreaseRate);
        currentHpMultiplier *= (1 + config.hpIncreaseRate);
        currentHeroShootCycleMultiplier *= (1 + config.heroShootCycleIncreaseRate);
        currentEnemyShootCycleMultiplier *= (1 - config.enemyShootCycleDecreaseRate);
        
        // 更新各项周期
        enemySpawnCycle = config.enemySpawnCycle * currentSpawnCycleMultiplier;
        heroShootCycle = config.heroShootCycle * currentHeroShootCycleMultiplier;
        enemyShootCycle = config.enemyShootCycle * currentEnemyShootCycleMultiplier;
        
        // 计算精英机概率（精英+精锐+王牌的总概率）
        double eliteProbability = config.enemySpawnWeights[1] + config.enemySpawnWeights[2] + config.enemySpawnWeights[3];
        
        System.out.println("提高难度！精英机概率：" + String.format("%.0f%%", eliteProbability * 100)
                + "，敌机周期：" + String.format("%.1f", enemySpawnCycle)
                + "，敌机属性提升倍率：" + String.format("%.2f", currentHpMultiplier));
    }

    @Override
    protected double getEnemySpawnCycle() {
        return enemySpawnCycle;
    }

    @Override
    protected boolean canSpawnBoss() {
        return config.canSpawnBoss;
    }

    @Override
    protected int getBossHp() {
        // 困难难度：每次召唤Boss血量递增
        return config.bossBaseHp + bossSpawnCounter * config.bossHpIncrement;
    }

    @Override
    protected void spawnEnemy() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            // 根据权重随机选择敌机类型
            int enemyType = getEnemyTypeByWeight();
            
            // 计算当前敌机属性（随时间递增）
            int speedY = (int) (config.enemyBaseSpeedY * currentSpeedMultiplier);
            int hp = (int) (config.enemyBaseHp * currentHpMultiplier);
            
            AbstractEnemyAircraft enemy = EnemyFactoryManager.createEnemy(
                    enemyType,
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    0,
                    speedY,
                    hp
            );
            enemyAircrafts.add(enemy);
            registerEnemyObserver(enemy);
        }
    }
    
    /**
     * 根据权重随机选择敌机类型
     */
    private int getEnemyTypeByWeight() {
        double random = Math.random();
        double[] weights = config.enemySpawnWeights;
        double cumulative = 0;
        
        for (int i = 0; i < weights.length; i++) {
            cumulative += weights[i];
            if (random < cumulative) {
                return i;
            }
        }
        return 0; // 默认普通敌机
    }
}
