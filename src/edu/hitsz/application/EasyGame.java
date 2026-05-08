package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.factory.EnemyFactoryManager;

/**
 * 简单难度游戏
 * 特点：
 * - 无法生成Boss
 * - 敌机生成周期长，敌机血量低，速度慢
 * - 英雄机射击周期短（射速快）
 * - 敌机射击周期长（射速慢）
 * - 只生成普通敌机和精英敌机
 * - 难度不随时间变化
 * @author AircraftWar
 */
public class EasyGame extends Game {

    // 当前敌机属性倍率
    private double currentHpMultiplier = 1.0;
    private double currentSpeedMultiplier = 1.0;

    public EasyGame() {
        super("easy");
    }

    @Override
    protected void loadDifficulty() {
        System.out.println("[EasyGame] 加载简单难度配置");
        this.config = DifficultyConfig.getEasyConfig();
    }

    @Override
    protected void increaseDifficulty() {
        // 简单难度：难度不随时间变化
        System.out.println("[EasyGame] 简单难度保持不变");
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
        return config.bossBaseHp;
    }

    @Override
    protected void spawnBoss() {
        // 简单难度不生成Boss
        System.out.println("[EasyGame] 简单难度无法生成Boss");
    }

    @Override
    protected void spawnEnemy() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            // 根据权重随机选择敌机类型
            int enemyType = getEnemyTypeByWeight();
            
            // 计算当前敌机属性（简单难度不随时间变化，倍率始终为1.0）
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
