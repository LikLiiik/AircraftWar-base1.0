package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import java.util.LinkedList;
import java.util.List;

/**
 * 王牌敌机
 */
public class AceEnemy extends AbstractEnemyAircraft {

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
}