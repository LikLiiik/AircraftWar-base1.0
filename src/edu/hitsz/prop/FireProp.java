package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.strategy.FirePowerUpStrategy;
import edu.hitsz.strategy.FirePowerUpStrategyImpl;

/**
 * 火力道具
 */
public class FireProp extends AbstractProp {

    private FirePowerUpStrategy powerUpStrategy;

    public FireProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.FIRE_PROP_IMAGE;
        this.width = ImageManager.FIRE_PROP_IMAGE.getWidth();
        this.height = ImageManager.FIRE_PROP_IMAGE.getHeight();
        this.powerUpStrategy = new FirePowerUpStrategyImpl();
    }

    @Override
    public void activate(HeroAircraft hero) {
        powerUpStrategy.activate(hero);
        System.out.println("FireSupply active!");
    }
}
