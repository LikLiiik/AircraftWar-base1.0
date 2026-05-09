package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 难度选择界面 - 参考 StartMenu 实现
 * @author AircraftWar
 */
public class DifficultySelection {

    private JPanel mainPanel;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    private JLabel titleLabel;
    
    // 难度选择监听器
    private DifficultyListener difficultyListener;

    public DifficultySelection() {
        // 初始化主面板
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(20, 20, 40));
        
        // 标题
        titleLabel = new JLabel("飞机大战", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0, 200, 255));
        titleLabel.setBounds(50, 100, 412, 60);
        mainPanel.add(titleLabel);
        
        // 副标题
        JLabel subtitleLabel = new JLabel("选择难度", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBounds(50, 180, 412, 40);
        mainPanel.add(subtitleLabel);
        
        // 按钮样式
        Dimension buttonSize = new Dimension(250, 60);
        Font buttonFont = new Font("微软雅黑", Font.BOLD, 20);
        
        // 简单难度按钮
        easyButton = createStyledButton("简单", new Color(0, 200, 100), buttonSize, buttonFont);
        easyButton.setBounds((512 - buttonSize.width) / 2, 280, buttonSize.width, buttonSize.height);
        mainPanel.add(easyButton);
        
        // 普通难度按钮
        normalButton = createStyledButton("普通", new Color(255, 200, 0), buttonSize, buttonFont);
        normalButton.setBounds((512 - buttonSize.width) / 2, 360, buttonSize.width, buttonSize.height);
        mainPanel.add(normalButton);
        
        // 困难难度按钮
        hardButton = createStyledButton("困难", new Color(255, 50, 50), buttonSize, buttonFont);
        hardButton.setBounds((512 - buttonSize.width) / 2, 440, buttonSize.width, buttonSize.height);
        mainPanel.add(hardButton);

        // 排行榜按钮
        Dimension lbButtonSize = new Dimension(250, 50);
        Font lbButtonFont = new Font("微软雅黑", Font.BOLD, 18);
        JButton leaderboardButton = createStyledButton("排行榜", new Color(100, 150, 255), lbButtonSize, lbButtonFont);
        leaderboardButton.setBounds((512 - lbButtonSize.width) / 2, 530, lbButtonSize.width, lbButtonSize.height);
        mainPanel.add(leaderboardButton);

        // 天赋系统按钮
        JButton talentButton = createStyledButton("天赋系统", new Color(200, 100, 255), lbButtonSize, lbButtonFont);
        talentButton.setBounds((512 - lbButtonSize.width) / 2, 600, lbButtonSize.width, lbButtonSize.height);
        mainPanel.add(talentButton);
        
        // 按钮监听器
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (difficultyListener != null) {
                    difficultyListener.onDifficultySelected("easy");
                }
            }
        });
        
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (difficultyListener != null) {
                    difficultyListener.onDifficultySelected("normal");
                }
            }
        });
        
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (difficultyListener != null) {
                    difficultyListener.onDifficultySelected("hard");
                }
            }
        });

        // 排行榜按钮监听器
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.show(Main.cardPanel, "leaderboard_selection");
            }
        });

        // 天赋系统按钮监听器
        talentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.show(Main.cardPanel, "talent");
            }
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
     * 设置难度选择监听器
     */
    public void setDifficultyListener(DifficultyListener listener) {
        this.difficultyListener = listener;
    }
    
    /**
     * 获取主面板
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    /**
     * 获取对应难度的背景图片
     */
    public static String getBackgroundImage(String difficulty) {
        switch (difficulty) {
            case "easy":
                return "src/images/bg.jpg";
            case "normal":
                return "src/images/bg2.jpg";
            case "hard":
                return "src/images/bg3.jpg";
            default:
                return "src/images/bg2.jpg";
        }
    }
    
    /**
     * 难度选择监听器接口
     */
    public interface DifficultyListener {
        void onDifficultySelected(String difficulty);
    }
}
