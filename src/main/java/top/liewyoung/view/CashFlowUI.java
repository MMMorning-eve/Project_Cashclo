package top.liewyoung.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Cashflow 游戏主界面 - 融合版
 * 结合了 MainGame 的配色与 RichGame 的逻辑，并增加了财务报表面板
 */
public class CashFlowUI extends JFrame {

    // --- 配色方案 (源自 MainGame.java) ---
    private final Color COLOR_MAP_BG = new Color(200, 227, 227);
    private final Color COLOR_SIDE_BG = new Color(230, 230, 177);
    private final Color COLOR_TILE_OPPORTUNITY = new Color(100, 200, 100); // 绿色                                                                 -机会
    private final Color COLOR_TILE_RISK = new Color(255, 100, 100);        // 红色-风险
    private final Color COLOR_TILE_NORMAL = new Color(240, 240, 240);      // 白色-普通

    // --- 核心组件 ---
    private MapPanel mapPanel;
    private DashboardPanel dashboardPanel;

    // --- 游戏数据 (模拟) ---
    private int playerIndex = 0; // 玩家在格子列表中的索引
    private final List<Tile> tiles = new ArrayList<>();

    // 模拟玩家数据
    private int cash = 5000;
    private int passiveIncome = 0;
    private int totalExpenses = 3000;
    private int salary = 8000;

    public CashFlowUI() {
        setTitle("AI Cashflow - 财务自由之路");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. 初始化地图数据 (回字形逻辑)
        initMapData();

        // 2. 左侧：地图面板
        mapPanel = new MapPanel();
        mapPanel.setPreferredSize(new Dimension(650, 700));
        add(mapPanel, BorderLayout.CENTER);

        // 3. 右侧：财务仪表盘
        dashboardPanel = new DashboardPanel();
        dashboardPanel.setPreferredSize(new Dimension(350, 700));
        add(dashboardPanel, BorderLayout.EAST);

        // 4. 初始刷新
        updateDashboard();
        setVisible(true);
    }

    // 初始化地图格子 (8x8 环形)
    private void initMapData() {
        int width = 8;
        int height = 8;
        // 上边
        for (int i = 0; i < width; i++) tiles.add(new Tile(i, 0, "机会"));
        // 右边
        for (int i = 1; i < height; i++) tiles.add(new Tile(width - 1, i, "市场"));
        // 下边
        for (int i = width - 2; i >= 0; i--) tiles.add(new Tile(i, height - 1, "命运"));
        // 左边
        for (int i = height - 2; i > 0; i--) tiles.add(new Tile(0, i, "银行"));

        // 修正起点
        tiles.get(0).type = "起点";
    }

    // --- 逻辑控制 ---

    private void handleRollDice() {
        dashboardPanel.btnRoll.setEnabled(false);
        int steps = (int) (Math.random() * 6) + 1;
        appendLog("🎲 你掷出了 " + steps + " 点");

        // 简单的动画计时器
        Timer timer = new Timer(150, new ActionListener() {
            int moved = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                playerIndex = (playerIndex + 1) % tiles.size();
                mapPanel.repaint();
                moved++;

                if (moved >= steps) {
                    ((Timer)e.getSource()).stop();
                    onPlayerLanded();
                }
            }
        });
        timer.start();
    }

    private void onPlayerLanded() {
        Tile currentTile = tiles.get(playerIndex);
        appendLog("📍 到达: " + currentTile.type);

        // 模拟事件触发 (这里将来接入你的 AI 逻辑)
        if (currentTile.type.equals("机会")) {
            int cost = 2000;
            if (cash >= cost) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "AI生成机会：隔壁老王急售二手房\n价格: $2000\n增加被动收入: +$300\n是否购买？",
                        "投资机会", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    cash -= cost;
                    passiveIncome += 300;
                    dashboardPanel.addAsset("老王二手房", 300);
                    appendLog("💰 购买资产成功！被动收入增加。");
                }
            } else {
                appendLog("💸 资金不足，错失良机。");
            }
        } else if (currentTile.type.equals("银行")) {
            cash += (salary - totalExpenses);
            appendLog("💵 发薪日！结算月现金流。");
        }

        updateDashboard();
        dashboardPanel.btnRoll.setEnabled(true);
        checkVictory();
    }

    private void checkVictory() {
        if (passiveIncome > totalExpenses) {
            JOptionPane.showMessageDialog(this, "🎉 恭喜！被动收入覆盖总支出，你实现了财务自由！");
        }
    }

    private void updateDashboard() {
        dashboardPanel.lblCash.setText(String.format("$%d", cash));
        dashboardPanel.lblPassive.setText(String.format("$%d", passiveIncome));
        dashboardPanel.lblExpenses.setText(String.format("$%d", totalExpenses));

        // 计算进度条
        int progress = totalExpenses == 0 ? 100 : (int)((double)passiveIncome / totalExpenses * 100);
        dashboardPanel.progressBar.setValue(Math.min(progress, 100));
        dashboardPanel.progressBar.setString("财务自由度: " + progress + "%");
    }

    private void appendLog(String text) {
        dashboardPanel.txtLog.append(text + "\n");
        dashboardPanel.txtLog.setCaretPosition(dashboardPanel.txtLog.getDocument().getLength());
    }

    // --- 内部类：地图面板 ---
    class MapPanel extends JPanel {
        private final int TILE_SIZE = 70;
        private final int MARGIN = 40;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 背景色
            g2d.setColor(COLOR_MAP_BG);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // 绘制格子
            for (int i = 0; i < tiles.size(); i++) {
                Tile t = tiles.get(i);
                int x = MARGIN + t.x * TILE_SIZE;
                int y = MARGIN + t.y * TILE_SIZE;

                // 根据类型决定颜色
                if (t.type.equals("起点")) g2d.setColor(Color.ORANGE);
                else if (t.type.equals("机会")) g2d.setColor(COLOR_TILE_OPPORTUNITY);
                else if (t.type.equals("命运")) g2d.setColor(COLOR_TILE_RISK);
                else g2d.setColor(COLOR_TILE_NORMAL);

                g2d.fillRoundRect(x, y, TILE_SIZE - 5, TILE_SIZE - 5, 10, 10);
                g2d.setColor(Color.GRAY);
                g2d.drawRoundRect(x, y, TILE_SIZE - 5, TILE_SIZE - 5, 10, 10);

                g2d.setColor(Color.BLACK);
                g2d.drawString(t.type, x + 10, y + TILE_SIZE / 2);
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                g2d.drawString(String.valueOf(i), x + 5, y + 15); // 序号
            }

            // 绘制玩家 (红色棋子)
            Tile playerTile = tiles.get(playerIndex);
            int px = MARGIN + playerTile.x * TILE_SIZE + TILE_SIZE / 4;
            int py = MARGIN + playerTile.y * TILE_SIZE + TILE_SIZE / 4;

            // 简单的阴影效果
            g2d.setColor(new Color(0,0,0,50));
            g2d.fillOval(px+3, py+3, TILE_SIZE / 2, TILE_SIZE / 2);

            g2d.setColor(Color.RED);
            g2d.fillOval(px, py, TILE_SIZE / 2, TILE_SIZE / 2);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(px, py, TILE_SIZE / 2, TILE_SIZE / 2);
        }
    }

    // --- 内部类：仪表盘面板 ---
    class DashboardPanel extends JPanel {
        JLabel lblCash, lblPassive, lblExpenses;
        JProgressBar progressBar;
        JTextArea txtLog;
        JButton btnRoll;
        DefaultTableModel assetModel;

        public DashboardPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(COLOR_SIDE_BG);
            setBorder(new EmptyBorder(10, 10, 10, 10));

            // 1. 核心数据区 (Grid)
            JPanel pnlStats = new JPanel(new GridLayout(3, 2, 5, 5));
            pnlStats.setOpaque(false);
            pnlStats.setBorder(new TitledBorder("财务状况"));

            pnlStats.add(new JLabel("手头现金:"));
            lblCash = new JLabel("$0");
            lblCash.setForeground(new Color(0, 100, 0));
            lblCash.setFont(new Font("Dialog", Font.BOLD, 14));
            pnlStats.add(lblCash);

            pnlStats.add(new JLabel("被动收入:"));
            lblPassive = new JLabel("$0");
            lblPassive.setForeground(Color.BLUE);
            pnlStats.add(lblPassive);

            pnlStats.add(new JLabel("总支出:"));
            lblExpenses = new JLabel("$0");
            lblExpenses.setForeground(Color.RED);
            pnlStats.add(lblExpenses);

            add(pnlStats);
            add(Box.createVerticalStrut(10));

            // 2. 进度条
            progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);
            progressBar.setForeground(new Color(34, 139, 34));
            add(progressBar);
            add(Box.createVerticalStrut(10));

            // 3. 资产负债表 (Table)
            JLabel lblAssets = new JLabel("资产列表 (Assets)");
            lblAssets.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(lblAssets);

            String[] colName = {"资产名称", "现金流"};
            assetModel = new DefaultTableModel(colName, 0);
            JTable tableAssets = new JTable(assetModel);
            JScrollPane scrollAssets = new JScrollPane(tableAssets);
            scrollAssets.setPreferredSize(new Dimension(300, 150));
            add(scrollAssets);
            add(Box.createVerticalStrut(10));

            // 4. 游戏日志 (Log)
            txtLog = new JTextArea();
            txtLog.setEditable(false);
            txtLog.setLineWrap(true);
            JScrollPane scrollLog = new JScrollPane(txtLog);
            scrollLog.setBorder(new TitledBorder("AI GM 日志"));
            add(scrollLog);
            add(Box.createVerticalStrut(10));

            // 5. 操作区
            btnRoll = new JButton("🎲 掷骰子 (Roll)");
            btnRoll.setFont(new Font("Dialog", Font.BOLD, 16));
            btnRoll.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnRoll.setMaximumSize(new Dimension(300, 50));
            btnRoll.addActionListener(e -> handleRollDice());
            add(btnRoll);
        }

        public void addAsset(String name, int flow) {
            assetModel.addRow(new Object[]{name, "+$" + flow});
        }
    }

    // --- 简单数据类 ---
    static class Tile {
        int x, y;
        String type;
        public Tile(int x, int y, String type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }

    // 主入口
    public static void main(String[] args) {
        try {
            // 尝试使用系统风格
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(CashFlowUI::new);
    }
}