package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;

/**
 * 排行榜难度选择界面
 * 用于选择查看不同难度的排行榜
 * @author AircraftWar
 */
public class LeaderboardSelection {

    private JPanel mainPanel;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    private JButton backButton;
    private JLabel titleLabel;

    public LeaderboardSelection() {
        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(20, 20, 40));

        // 标题
        titleLabel = new JLabel("排行榜", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0, 200, 255));
        titleLabel.setBounds(50, 100, 412, 60);
        mainPanel.add(titleLabel);

        // 副标题
        JLabel subtitleLabel = new JLabel("选择难度查看排行榜", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBounds(50, 180, 412, 40);
        mainPanel.add(subtitleLabel);

        // 按钮样式
        Dimension buttonSize = new Dimension(250, 60);
        Font buttonFont = new Font("微软雅黑", Font.BOLD, 20);

        // 简单难度按钮
        easyButton = createStyledButton("简单难度", new Color(0, 200, 100), buttonSize, buttonFont);
        easyButton.setBounds((512 - buttonSize.width) / 2, 280, buttonSize.width, buttonSize.height);
        mainPanel.add(easyButton);

        // 普通难度按钮
        normalButton = createStyledButton("普通难度", new Color(255, 200, 0), buttonSize, buttonFont);
        normalButton.setBounds((512 - buttonSize.width) / 2, 360, buttonSize.width, buttonSize.height);
        mainPanel.add(normalButton);

        // 困难难度按钮
        hardButton = createStyledButton("困难难度", new Color(255, 50, 50), buttonSize, buttonFont);
        hardButton.setBounds((512 - buttonSize.width) / 2, 440, buttonSize.width, buttonSize.height);
        mainPanel.add(hardButton);

        // 返回按钮
        backButton = createStyledButton("返回主菜单", new Color(150, 150, 150), new Dimension(200, 50), 
            new Font("微软雅黑", Font.BOLD, 18));
        backButton.setBounds((512 - 200) / 2, 560, 200, 50);
        mainPanel.add(backButton);

        // 按钮监听器
        easyButton.addActionListener(e -> openLeaderboard("easy"));
        normalButton.addActionListener(e -> openLeaderboard("normal"));
        hardButton.addActionListener(e -> openLeaderboard("hard"));
        backButton.addActionListener(e -> {
            Main.cardLayout.show(Main.cardPanel, "menu");
        });
    }

    /**
     * 创建样式化按钮
     */
    private JButton createStyledButton(String text, Color color, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    /**
     * 打开对应难度的排行榜
     */
    private void openLeaderboard(String difficulty) {
        LeaderboardUI leaderboardUI = new LeaderboardUI(0, difficulty);
        Main.cardPanel.add(leaderboardUI.getMainPanel(), "leaderboard");
        Main.cardLayout.show(Main.cardPanel, "leaderboard");
    }

    /**
     * 获取主面板
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
