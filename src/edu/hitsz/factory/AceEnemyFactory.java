package edu.hitsz.factory;

import edu.hitsz.aircraft.AceEnemy;

/**
 * 王牌敌机工厂
 * 负责创建王牌敌机（AceEnemy）
 * @author hitsz
 */
public class AceEnemyFactory implements EnemyFactory {

    @Override
    public edu.hitsz.aircraft.AbstractEnemyAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new AceEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
