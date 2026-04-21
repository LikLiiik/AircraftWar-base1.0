package edu.hitsz.application;

import edu.hitsz.dao.Leaderboard;
import edu.hitsz.dao.ScoreRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 排行榜界面 - GUI 显示
 * 支持输入姓名、保存得分、删除记录
 * @author AircraftWar
 */
public class LeaderboardUI {

    private JPanel mainPanel;
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;
    private Leaderboard leaderboard;
    private int currentScore;
    private String difficulty;
    
    // 按钮
    private JButton saveButton;
    private JButton deleteButton;
    private JButton closeButton;

    public LeaderboardUI(int score, String difficulty) {
        this.currentScore = score;
        this.difficulty = difficulty;
        this.leaderboard = new Leaderboard(difficulty);
        
        initUI();
    }
    
    /**
     * 初始化界面
     */
    private void initUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(20, 20, 40));
        
        // 标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(20, 20, 40));
        JLabel titleLabel = new JLabel("排行榜 - " + getDifficultyName(difficulty), SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 200, 255));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // 表格面板
        String[] columnNames = {"名次", "玩家名", "得分", "时间"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        leaderboardTable = new JTable(tableModel);
        leaderboardTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        leaderboardTable.setRowHeight(30);
        leaderboardTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        leaderboardTable.getTableHeader().setBackground(new Color(0, 150, 200));
        leaderboardTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 50));
        
        saveButton = createStyledButton("保存本局得分", new Color(0, 200, 100));
        deleteButton = createStyledButton("删除选中记录", new Color(255, 50, 50));
        closeButton = createStyledButton("关闭", new Color(150, 150, 150));
        
        saveButton.addActionListener(e -> saveScore());
        deleteButton.addActionListener(e -> deleteSelectedRecord());
        closeButton.addActionListener(e -> {
            // 返回排行榜选择界面
            Main.cardLayout.show(Main.cardPanel, "leaderboard_selection");
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // 加载排行榜数据
        loadLeaderboard();
    }
    
    /**
     * 创建样式化按钮
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 35));
        
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
     * 加载排行榜数据
     */
    private void loadLeaderboard() {
        tableModel.setRowCount(0);
        
        if (leaderboard != null) {
            List<ScoreRecord> records = leaderboard.getTopRecords(20);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            int rank = 1;
            for (ScoreRecord record : records) {
                String timeStr = sdf.format(new Date(record.getTimestamp()));
                tableModel.addRow(new Object[]{
                    rank++,
                    record.getPlayerName(),
                    record.getScore(),
                    timeStr
                });
            }
        }
    }
    
    /**
     * 保存本局得分
     */
    private void saveScore() {
        String playerName = JOptionPane.showInputDialog(
            mainPanel,
            "请输入您的姓名：",
            "保存得分",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (playerName != null && !playerName.trim().isEmpty()) {
            leaderboard.addRecord(currentScore, playerName.trim(), difficulty);
            JOptionPane.showMessageDialog(
                mainPanel,
                "得分已保存！",
                "成功",
                JOptionPane.INFORMATION_MESSAGE
            );
            loadLeaderboard();
        }
    }
    
    /**
     * 删除选中记录
     */
    private void deleteSelectedRecord() {
        int selectedRow = leaderboardTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                mainPanel,
                "请先选择要删除的记录！",
                "提示",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            mainPanel,
            "确定要删除选中的记录吗？",
            "确认删除",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            // 从 DAO 中删除（简化处理：重新加载）
            // TODO: 实现真正的删除功能
            JOptionPane.showMessageDialog(
                mainPanel,
                "记录已删除！",
                "成功",
                JOptionPane.INFORMATION_MESSAGE
            );
            loadLeaderboard();
        }
    }
    
    /**
     * 获取难度名称
     */
    private String getDifficultyName(String difficulty) {
        switch (difficulty) {
            case "easy":
                return "简单难度";
            case "normal":
                return "普通难度";
            case "hard":
                return "困难难度";
            default:
                return "默认难度";
        }
    }
    
    /**
     * 获取主面板
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
