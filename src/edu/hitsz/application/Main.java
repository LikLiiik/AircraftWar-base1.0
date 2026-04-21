package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口 - 参考 CardLayoutDemo 实现
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    
    // CardLayout 用于界面切换
    static final CardLayout cardLayout = new CardLayout(0, 0);
    static final JPanel cardPanel = new JPanel(cardLayout);

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置，居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(cardPanel);
        
        // 创建难度选择界面
        DifficultySelection difficultySelection = new DifficultySelection();
        cardPanel.add(difficultySelection.getMainPanel(), "menu");
        
        // 创建排行榜选择界面
        LeaderboardSelection leaderboardSelection = new LeaderboardSelection();
        cardPanel.add(leaderboardSelection.getMainPanel(), "leaderboard_selection");
        
        frame.setVisible(true);
        
        // 设置难度选择监听器
        difficultySelection.setDifficultyListener(new DifficultySelection.DifficultyListener() {
            @Override
            public void onDifficultySelected(String difficulty) {
                System.out.println("选择难度：" + difficulty);
                
                // 移除游戏面板和排行榜面板（如果存在），保留 menu 和 leaderboard_selection
                Component[] components = cardPanel.getComponents();
                for (int i = components.length - 1; i >= 0; i--) {
                    Component comp = components[i];
                    // 移除 Game 和 LeaderboardUI 类型的面板
                    if (comp instanceof Game || comp.getClass().getSimpleName().equals("LeaderboardUI")) {
                        cardPanel.remove(comp);
                    }
                }
                
                // 创建游戏实例并传入难度
                Game game = new Game(difficulty);
                cardPanel.add(game, "game");
                
                // 切换到游戏界面
                cardLayout.show(cardPanel, "game");
                
                // 开始游戏
                game.action();
            }
        });
    }
}
