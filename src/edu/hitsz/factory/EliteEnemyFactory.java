package edu.hitsz.factory;

import edu.hitsz.aircraft.EliteEnemy;

/**
 * 精英敌机工厂
 * 负责创建精英敌机（EliteEnemy）
 * @author hitsz
 */
public class EliteEnemyFactory implements EnemyFactory {

    @Override
    public edu.hitsz.aircraft.AbstractEnemyAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
