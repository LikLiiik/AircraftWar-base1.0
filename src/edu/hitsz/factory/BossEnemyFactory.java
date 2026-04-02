package edu.hitsz.factory;

import edu.hitsz.aircraft.BossEnemy;

/**
 * Boss敌机工厂
 * 负责创建Boss敌机（BossEnemy）
 * @author hitsz
 */
public class BossEnemyFactory implements EnemyFactory {

    @Override
    public edu.hitsz.aircraft.AbstractEnemyAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new BossEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
