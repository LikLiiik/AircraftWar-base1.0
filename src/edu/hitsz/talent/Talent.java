package edu.hitsz.talent;

/**
 * 天赋数据类
 * @author AircraftWar
 */
public class Talent {
    private String id;
    private String name;
    private String description;
    private int maxLevel;
    private int costPerLevel;
    private int currentLevel;
    private int effectPerLevel; // 每级效果值

    public Talent(String id, String name, String description, int maxLevel, int costPerLevel) {
        this(id, name, description, maxLevel, costPerLevel, 1);
    }

    public Talent(String id, String name, String description, int maxLevel, int costPerLevel, int effectPerLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.costPerLevel = costPerLevel;
        this.effectPerLevel = effectPerLevel;
        this.currentLevel = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getMaxLevel() { return maxLevel; }
    public int getCostPerLevel() { return costPerLevel; }
    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int level) { this.currentLevel = level; }

    /**
     * 获取升级所需天赋点
     */
    public int getUpgradeCost() {
        if (currentLevel >= maxLevel) return -1;
        return costPerLevel;
    }

    /**
     * 是否可以升级
     */
    public boolean canUpgrade() {
        return currentLevel < maxLevel;
    }

    /**
     * 升级
     */
    public boolean upgrade() {
        if (canUpgrade()) {
            currentLevel++;
            return true;
        }
        return false;
    }

    /**
     * 获取当前等级的效果描述
     */
    public String getCurrentEffect() {
        return String.format(description, currentLevel * effectPerLevel);
    }

    /**
     * 获取下一级的效果描述
     */
    public String getNextEffect() {
        if (currentLevel >= maxLevel) return "已满级";
        return String.format(description, (currentLevel + 1) * effectPerLevel);
    }
}
