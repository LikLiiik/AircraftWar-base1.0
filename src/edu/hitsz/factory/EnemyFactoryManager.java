package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;

/**
 * 敌机工厂管理器
 * 统一管理敌机工厂的创建和选择
 * @author hitsz
 */
public class EnemyFactoryManager {

    private static final int TYPE_MOB = 0;
    private static final int TYPE_ELITE = 1;
    private static final int TYPE_ELITE_PLUS = 2;
    private static final int TYPE_ACE = 3;
    private static final int TYPE_BOSS = 4;

    /**
     * 根据敌机类型获取对应的工厂
     * @param enemyType 敌机类型
     * @return 对应的敌机工厂
     */
    public static EnemyFactory getFactory(int enemyType) {
        switch (enemyType) {
            case TYPE_MOB:
                return new MobEnemyFactory();
            case TYPE_ELITE:
                return new EliteEnemyFactory();
            case TYPE_ELITE_PLUS:
                return new ElitePlusEnemyFactory();
            case TYPE_ACE:
                return new AceEnemyFactory();
            case TYPE_BOSS:
                return new BossEnemyFactory();
            default:
                return new MobEnemyFactory();
        }
    }

    /**
     * 根据敌机类型创建敌机对象
     * @param enemyType 敌机类型
     * @param locationX 敌机位置X
     * @param locationY 敌机位置Y
     * @param speedX 敌机速度X
     * @param speedY 敌机速度Y
     * @param hp 敌机生命值
     * @return 敌机对象
     */
    public static AbstractEnemyAircraft createEnemy(int enemyType, int locationX, int locationY, int speedX, int speedY, int hp) {
        EnemyFactory factory = getFactory(enemyType);
        return factory.createAircraft(locationX, locationY, speedX, speedY, hp);
    }
}
