package edu.hitsz.factory;

import edu.hitsz.aircraft.ElitePlusEnemy;

/**
 * 精锐敌机工厂
 * 负责创建精锐敌机（ElitePlusEnemy）
 * @author hitsz
 */
public class ElitePlusEnemyFactory implements EnemyFactory {

    @Override
    public edu.hitsz.aircraft.AbstractEnemyAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new ElitePlusEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
