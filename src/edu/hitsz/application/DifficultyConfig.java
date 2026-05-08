package edu.hitsz.application;

/**
 * 难度配置类
 * 统一管理三种难度的所有游戏参数
 */
public class DifficultyConfig {

    // ==================== 难度类型 ====================
    public enum Difficulty {
        EASY, NORMAL, HARD
    }

    // ==================== 初始设定参数 ====================
    // 屏幕中出现的敌机最大数量
    public int enemyMaxNumber;

    // 不同类型敌机的出现概率 [mob, elite, elitePlus, ace]
    public double[] enemySpawnWeights;

    // 英雄机的射击周期（帧数，越小越快）
    public double heroShootCycle;

    // 敌机的射击周期（帧数，越小越快）
    public double enemyShootCycle;

    // 敌机产生周期（帧数，越小生成越快）
    public double enemySpawnCycle;

    // Boss敌机产生的阈值（分数）
    public int bossScoreThreshold;

    // ==================== 敌机基础属性 ====================
    // 敌机基础速度Y
    public int enemyBaseSpeedY;
    // 敌机基础血量
    public int enemyBaseHp;

    // ==================== Boss设定 ====================
    // 是否可以生成Boss
    public boolean canSpawnBoss;
    // Boss基础血量
    public int bossBaseHp;
    // 每次召唤Boss血量是否递增
    public boolean bossHpIncrease;
    // Boss血量递增数值
    public int bossHpIncrement;

    // ==================== 随时间变化参数 ====================
    // 难度是否随时间变化
    public boolean difficultyIncreaseOverTime;
    // 敌机产生周期变化率（每30秒减少的比例，0表示不变）
    public double spawnCycleDecreaseRate;
    // 敌机速度增加率（每30秒增加的比例，0表示不变）
    public double speedIncreaseRate;
    // 敌机血量增加率（每30秒增加的比例，0表示不变）
    public double hpIncreaseRate;
    // 英雄机射击周期变化率（每30秒增加的比例，变慢，0表示不变）
    public double heroShootCycleIncreaseRate;
    // 敌机射击周期变化率（每30秒减少的比例，变快，0表示不变）
    public double enemyShootCycleDecreaseRate;

    // ==================== 构造方法 ====================
    private DifficultyConfig() {}

    /**
     * 获取简单难度配置
     */
    public static DifficultyConfig getEasyConfig() {
        DifficultyConfig config = new DifficultyConfig();

        // 初始设定
        config.enemyMaxNumber = 4;
        config.enemySpawnWeights = new double[]{0.6, 0.2, 0.1, 0.1}; // 60%普通，20%精英，10%精锐，10%王牌
        config.heroShootCycle = 18; // 英雄机射击较快
        config.enemyShootCycle = 30; // 敌机射击较慢
        config.enemySpawnCycle = 30; // 敌机生成较慢
        config.bossScoreThreshold = 500;

        // 敌机基础属性
        config.enemyBaseSpeedY = 5;
        config.enemyBaseHp = 20;

        // Boss设定：简单难度无法生成Boss
        config.canSpawnBoss = false;
        config.bossBaseHp = 0;
        config.bossHpIncrease = false;
        config.bossHpIncrement = 0;

        // 随时间变化：简单难度不变化
        config.difficultyIncreaseOverTime = false;
        config.spawnCycleDecreaseRate = 0.0;
        config.speedIncreaseRate = 0.0;
        config.hpIncreaseRate = 0.0;
        config.heroShootCycleIncreaseRate = 0.0;
        config.enemyShootCycleDecreaseRate = 0.0;

        return config;
    }

    /**
     * 获取普通难度配置
     */
    public static DifficultyConfig getNormalConfig() {
        DifficultyConfig config = new DifficultyConfig();

        // 初始设定
        config.enemyMaxNumber = 5;
        config.enemySpawnWeights = new double[]{0.4, 0.3, 0.2, 0.1}; // 40%普通，30%精英，20%精锐，10%王牌
        config.heroShootCycle = 20; // 标准
        config.enemyShootCycle = 25; // 标准
        config.enemySpawnCycle = 25; // 标准
        config.bossScoreThreshold = 500;

        // 敌机基础属性
        config.enemyBaseSpeedY = 8;
        config.enemyBaseHp = 30;

        // Boss设定：普通难度可以生成Boss，血量固定
        config.canSpawnBoss = true;
        config.bossBaseHp = 200;
        config.bossHpIncrease = false;
        config.bossHpIncrement = 0;

        // 随时间变化：普通难度部分参数变化
        config.difficultyIncreaseOverTime = true;
        config.spawnCycleDecreaseRate = 0.03; // 每30秒生成周期减少3%
        config.speedIncreaseRate = 0.03; // 每30秒速度增加3%
        config.hpIncreaseRate = 0.03; // 每30秒血量增加3%
        config.heroShootCycleIncreaseRate = 0.0; // 英雄机射击周期不变
        config.enemyShootCycleDecreaseRate = 0.0; // 敌机射击周期不变

        return config;
    }

    /**
     * 获取困难难度配置
     */
    public static DifficultyConfig getHardConfig() {
        DifficultyConfig config = new DifficultyConfig();

        // 初始设定
        config.enemyMaxNumber = 6;
        config.enemySpawnWeights = new double[]{0.2, 0.3, 0.3, 0.2}; // 20%普通，30%精英，30%精锐，20%王牌
        config.heroShootCycle = 22; // 英雄机射击较慢
        config.enemyShootCycle = 20; // 敌机射击较快
        config.enemySpawnCycle = 20; // 敌机生成较快
        config.bossScoreThreshold = 500; // Boss阈值

        // 敌机基础属性
        config.enemyBaseSpeedY = 10;
        config.enemyBaseHp = 40;

        // Boss设定：困难难度可以生成Boss，血量递增
        config.canSpawnBoss = true;
        config.bossBaseHp = 250;
        config.bossHpIncrease = true;
        config.bossHpIncrement = 100;

        // 随时间变化：困难难度全部参数变化
        config.difficultyIncreaseOverTime = true;
        config.spawnCycleDecreaseRate = 0.05; // 每30秒生成周期减少5%
        config.speedIncreaseRate = 0.05; // 每30秒速度增加5%
        config.hpIncreaseRate = 0.05; // 每30秒血量增加5%
        config.heroShootCycleIncreaseRate = 0.05; // 每30秒英雄机射击周期增加5%（变慢）
        config.enemyShootCycleDecreaseRate = 0.05; // 每30秒敌机射击周期减少5%（变快）

        return config;
    }

    /**
     * 根据难度字符串获取配置
     */
    public static DifficultyConfig getConfig(String difficulty) {
        return switch (difficulty.toLowerCase()) {
            case "easy" -> getEasyConfig();
            case "normal" -> getNormalConfig();
            case "hard" -> getHardConfig();
            default -> getNormalConfig();
        };
    }
}
