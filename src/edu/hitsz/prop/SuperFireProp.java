package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.SoundManager;
import edu.hitsz.strategy.FirePowerUpStrategy;
import edu.hitsz.strategy.FirePlusPowerUpStrategyImpl;

/**
 * 超级火力道具 - 持续 15 秒后自动恢复直射
 * 使用线程实现定时恢复
 */
public class SuperFireProp extends AbstractProp {

    private FirePowerUpStrategy powerUpStrategy;
    private static final int EFFECT_DURATION = 15000; // 15 秒

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
        SoundManager.getInstance().playGetSupply();
        System.out.println("FirePlusSupply active! 环射弹道已启用，15 秒后自动恢复");
        
        // 使用线程实现定时恢复
        Thread restoreThread = new Thread(new RestoreTask(hero, EFFECT_DURATION));
        restoreThread.start();
    }
    
    /**
     * 恢复任务 - 使用线程等待后恢复
     */
    private static class RestoreTask implements Runnable {
        private HeroAircraft hero;
        private int duration;
        
        public RestoreTask(HeroAircraft hero, int duration) {
            this.hero = hero;
            this.duration = duration;
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep(duration);
                hero.resetShootStrategy();
                System.out.println("FirePlusSupply expired! 已恢复直射弹道");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
