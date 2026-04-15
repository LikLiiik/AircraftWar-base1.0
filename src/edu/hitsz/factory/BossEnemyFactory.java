package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;

/**
 * Boss 敌机工厂
 * 负责创建 Boss 敌机对象
 */
public class BossEnemyFactory implements EnemyFactory {
    
    @Override
    public AbstractEnemyAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new BossEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
