package edu.hitsz.observer;

/**
 * 观察者接口 - 用于炸弹、冰冻道具效果通知
 * @author AircraftWar
 */
public interface Observer {
    /**
     * 接收通知
     * @param event 事件类型 ("bomb", "freeze")
     * @param duration 持续时间（毫秒，仅冰冻事件有效）
     */
    void onNotify(String event, int duration);
}
