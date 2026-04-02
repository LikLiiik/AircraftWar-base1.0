package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;

/**
 * 敌机工厂接口（工厂方法模式）
 * 声明创建敌机对象的方法
 * @author hitsz
 */
public interface EnemyFactory {

    /**
     * 创建敌机对象
     * @param locationX 敌机位置X
     * @param locationY 敌机位置Y
     * @param speedX 敌机速度X
     * @param speedY 敌机速度Y
     * @param hp 敌机生命值
     * @return 敌机对象
     */
    AbstractEnemyAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp);
}
