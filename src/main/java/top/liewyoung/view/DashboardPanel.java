package top.liewyoung.view;

import javax.swing.*;
import java.awt.*;

/**
 * 简单的仪表盘
 *
 * @author LiewYoung
 * @since 2025/12/10
 */

public class DashboardPanel extends JPanel {
    private final JPanel contentSheet;//内容面板
    private final JPanel headerContent;//表头
    private final JPanel perContent;//资产仪表板
    private final JPanel dataPanel;
    private double income;//收入
    private double outcome;//支出
    private double depreciation;//折旧
    private double cost;//维护花费
    private JPanel eventLog;

    public DashboardPanel() {
        contentSheet = new JPanel(new GridLayout(2, 4, 5, 5));
        JLabel title = new JLabel("资产损益表");

        // 收入支出
        JLabel income = new JLabel("收入", SwingConstants.LEFT);
        JLabel incomeValue = new JLabel(String.valueOf(this.income));
        JLabel outcome = new JLabel("支出", SwingConstants.LEFT);
        JLabel outcomeValue = new JLabel(String.valueOf(this.outcome));


        contentSheet.add(title);

        //维护卡片布局整齐
        for (int i = 0; i < 3; i++) {
            contentSheet.add(new JLabel());
        }

        contentSheet.add(income);
        contentSheet.add(incomeValue);
        contentSheet.add(outcome);
        contentSheet.add(outcomeValue);


        headerContent = new JPanel(new GridLayout(1, 4, 5, 5));
        headerContent.add(new JLabel("资产"));
        headerContent.add(new JLabel("贬值"));
        headerContent.add(new JLabel("支出"));
        headerContent.add(new JLabel("状态"));
        headerContent.setOpaque(true);
        headerContent.setBackground(Color.LIGHT_GRAY);

        perContent = new JPanel(new GridLayout(2, 1, 5, 5));
        perContent.add(headerContent);

        dataPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        for (int i = 0; i < 4; i++) {
            JPanel values = new JPanel(new GridLayout(1, 4, 5, 5));
            values.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            values.add(new JLabel("1000"));
            values.add(new JLabel("-100"));
            values.add(new JLabel("-100"));
            values.add(new JLabel("正常"));
            dataPanel.add(values);
        }

        JScrollPane scrollPane = new JScrollPane(dataPanel);
        perContent.add(scrollPane);

        headerContent.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        contentSheet.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        eventLog = new JPanel(new GridLayout(0, 1, 5, 5));
        eventLog.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        eventLog.setBackground(Color.pink);
        eventLog.add(new JLabel("事件日志"));
        JScrollPane eventLogScrollPane = new JScrollPane(eventLog);

        setLayout(new GridLayout(0, 1, 10, 5));
        add(contentSheet);
        add(perContent);
        add(eventLogScrollPane);

       setBorder(BorderFactory.createEmptyBorder(13, 13, 13, 13));
       setSize(500,getHeight());

    }

    public void InsertData(String name, double depreciation, double outcome, String  status) {
        JPanel row = new JPanel(new GridLayout(1, 4, 5, 5));
        row.add(new JLabel(name));
        row.add(new JLabel(String.valueOf(depreciation)));
        row.add(new JLabel(String.valueOf(outcome)));
        row.add(new JLabel(status));

        dataPanel.add(row);
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new DashboardPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);

    }
}
