package edu.hitsz.application;

import edu.hitsz.talent.PlayerTalent;
import edu.hitsz.talent.Talent;
import edu.hitsz.talent.TalentSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 天赋系统界面
 * @author AircraftWar
 */
public class TalentUI {

    private JPanel mainPanel;
    private JLabel playerLabel;
    private JLabel scoreLabel;
    private JLabel pointsLabel;
    private JPanel talentPanel;
    private PlayerTalent currentPlayer;

    public TalentUI() {
        initUI();
    }

    private void initUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(20, 20, 40));

        // 顶部信息面板
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBackground(new Color(20, 20, 40));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerLabel = new JLabel("请选择玩家", SwingConstants.CENTER);
        playerLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        playerLabel.setForeground(Color.WHITE);

        scoreLabel = new JLabel("累计得分：0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        scoreLabel.setForeground(new Color(0, 200, 255));

        pointsLabel = new JLabel("可用天赋点：0", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        pointsLabel.setForeground(new Color(255, 200, 0));

        JLabel hintLabel = new JLabel("每获取10000玩家得分自动增加1天赋点", SwingConstants.CENTER);
        hintLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        hintLabel.setForeground(new Color(150, 150, 150));

        infoPanel.add(playerLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(pointsLabel);
        infoPanel.add(hintLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // 天赋列表面板
        talentPanel = new JPanel();
        talentPanel.setLayout(new BoxLayout(talentPanel, BoxLayout.Y_AXIS));
        talentPanel.setBackground(new Color(20, 20, 40));
        talentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(talentPanel);
        scrollPane.setBackground(new Color(20, 20, 40));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 底部按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 50));

        JButton selectPlayerButton = createStyledButton("选择玩家", new Color(0, 150, 200));
        JButton backButton = createStyledButton("返回主菜单", new Color(150, 150, 150));

        selectPlayerButton.addActionListener(e -> showPlayerSelection());
        backButton.addActionListener(e -> Main.cardLayout.show(Main.cardPanel, "menu"));

        buttonPanel.add(selectPlayerButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * 显示玩家选择对话框
     */
    private void showPlayerSelection() {
        // 从排行榜获取所有玩家名
        String playerName = JOptionPane.showInputDialog(
            mainPanel,
            "请输入玩家名称：",
            "选择玩家",
            JOptionPane.QUESTION_MESSAGE
        );

        if (playerName != null && !playerName.trim().isEmpty()) {
            loadPlayer(playerName.trim());
        }
    }

    /**
     * 加载玩家天赋数据
     */
    public void loadPlayer(String playerName) {
        currentPlayer = TalentSystem.getInstance().loadPlayer(playerName);
        updateUI();
    }

    /**
     * 更新界面显示
     */
    private void updateUI() {
        if (currentPlayer == null) return;

        playerLabel.setText("玩家：" + currentPlayer.getPlayerName());
        scoreLabel.setText("累计得分：" + currentPlayer.getTotalScore());
        pointsLabel.setText("可用天赋点：" + currentPlayer.getAvailableTalentPoints());

        // 清空并重新加载天赋列表
        talentPanel.removeAll();
        List<Talent> talents = TalentSystem.getInstance().getAllTalents();

        for (Talent talent : talents) {
            JPanel talentItem = createTalentItem(talent);
            talentPanel.add(talentItem);
            talentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        talentPanel.revalidate();
        talentPanel.repaint();
    }

    /**
     * 创建天赋项面板
     */
    private JPanel createTalentItem(Talent talent) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(40, 40, 60));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 150, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // 左侧信息
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(new Color(40, 40, 60));

        JLabel nameLabel = new JLabel(talent.getName() + " (等级 " + talent.getCurrentLevel() + "/" + talent.getMaxLevel() + ")");
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);

        JLabel effectLabel = new JLabel("当前效果：" + talent.getCurrentEffect());
        effectLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        effectLabel.setForeground(new Color(0, 200, 255));

        JLabel nextEffectLabel = new JLabel("下一级：" + talent.getNextEffect());
        nextEffectLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        nextEffectLabel.setForeground(Color.GRAY);

        infoPanel.add(nameLabel);
        infoPanel.add(effectLabel);
        infoPanel.add(nextEffectLabel);

        panel.add(infoPanel, BorderLayout.CENTER);

        // 右侧升级按钮
        if (talent.canUpgrade()) {
            JButton upgradeButton = createStyledButton("升级 (" + talent.getCostPerLevel() + "点)", new Color(0, 200, 100));
            upgradeButton.setPreferredSize(new Dimension(120, 40));
            upgradeButton.addActionListener(e -> {
                if (currentPlayer != null) {
                    boolean success = TalentSystem.getInstance().upgradeTalent(currentPlayer, talent.getId());
                    if (success) {
                        JOptionPane.showMessageDialog(mainPanel, "升级成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                        updateUI();
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "天赋点不足！", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            panel.add(upgradeButton, BorderLayout.EAST);
        } else {
            JLabel maxLabel = new JLabel("已满级", SwingConstants.CENTER);
            maxLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
            maxLabel.setForeground(new Color(255, 200, 0));
            maxLabel.setPreferredSize(new Dimension(120, 40));
            panel.add(maxLabel, BorderLayout.EAST);
        }

        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
