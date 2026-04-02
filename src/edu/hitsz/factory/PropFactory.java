package edu.hitsz.factory;

import edu.hitsz.prop.*;

/**
 * 道具工厂类（简单工厂模式）
 * 负责创建各种类型的道具
 * @author hitsz
 */
public class PropFactory {

    /**
     * 根据道具类型创建对应的道具对象
     * @param propType 道具类型（0: 加血, 1: 火力, 2: 超级火力, 3: 冰冻, 4: 炸弹）
     * @param locationX 道具位置X
     * @param locationY 道具位置Y
     * @param speedX 道具速度X
     * @param speedY 道具速度Y
     * @return 对应的道具对象
     */
    public static AbstractProp createProp(int propType, int locationX, int locationY, int speedX, int speedY) {
        switch (propType) {
            case 0:
                return new BloodProp(locationX, locationY, speedX, speedY);
            case 1:
                return new FireProp(locationX, locationY, speedX, speedY);
            case 2:
                return new SuperFireProp(locationX, locationY, speedX, speedY);
            case 3:
                return new FreezeProp(locationX, locationY, speedX, speedY);
            case 4:
                return new BombProp(locationX, locationY, speedX, speedY);
            default:
                return null;
        }
    }

    /**
     * 根据随机类型创建道具
     * @param locationX 道具位置X
     * @param locationY 道具位置Y
     * @param speedX 道具速度X
     * @param speedY 道具速度Y
     * @return 随机类型的道具对象
     */
    public static AbstractProp createRandomProp(int locationX, int locationY, int speedX, int speedY) {
        int propType = (int) (Math.random() * 5);
        return createProp(propType, locationX, locationY, speedX, speedY);
    }
}
