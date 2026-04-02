package edu.hitsz.factory;

import edu.hitsz.aircraft.MobEnemy;

/**
 * 普通敌机工厂
 * 负责创建普通敌机（MobEnemy）
 * @author hitsz
 */
public class MobEnemyFactory implements EnemyFactory {

    @Override
    public edu.hitsz.aircraft.AbstractEnemyAircraft createAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new MobEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
