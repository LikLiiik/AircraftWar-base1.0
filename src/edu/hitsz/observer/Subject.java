package edu.hitsz.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者基类 - 管理观察者列表
 * @author AircraftWar
 */
public class Subject {
    
    private List<Observer> observers = new ArrayList<>();
    
    /**
     * 添加观察者
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    /**
     * 移除观察者
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    /**
     * 通知所有观察者
     * @param event 事件类型
     * @param duration 持续时间
     */
    public void notifyObservers(String event, int duration) {
        for (Observer observer : observers) {
            observer.onNotify(event, duration);
        }
    }
    
    /**
     * 移除所有观察者
     */
    public void removeAllObservers() {
        observers.clear();
    }
}
