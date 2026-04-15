package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.strategy.FirePowerUpStrategy;
import edu.hitsz.strategy.FirePlusPowerUpStrategyImpl;

/**
 * 超级火力道具
 */
public class SuperFireProp extends AbstractProp {

    private FirePowerUpStrategy powerUpStrategy;

    public SuperFireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.SUPER_FIRE_PROP_IMAGE;
        this.width = ImageManager.SUPER_FIRE_PROP_IMAGE.getWidth();
        this.height = ImageManager.SUPER_FIRE_PROP_IMAGE.getHeight();
        this.powerUpStrategy = new FirePlusPowerUpStrategyImpl();
    }

    @Override
    public void activate(HeroAircraft hero) {
        powerUpStrategy.activate(hero);
        System.out.println("FirePlusSupply active!");
    }
}
