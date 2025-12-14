package top.liewyoung.view.component;

import top.liewyoung.view.ColorSystem.MaterialPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SelectDialog extends JDialog {
    private final MaterialPalette palette = MaterialPalette.MOSS;

    private boolean isConfirmed = false; // 用户是否点击了确定
    private JComboBox<String> comboBox;
    private String selectedValue;

    public SelectDialog(Frame owner) {
        // super(owner, title, modal) -> 设置 modal 为 true，阻塞父窗口
        super(owner, "请选择职业", true);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 20)); // 垂直间距20
        contentPanel.setBackground(palette.surface());
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30)); //四周留白，增加呼吸感
        setContentPane(contentPanel);

        JLabel titleLabel = new JLabel("请选择您的初始职业");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        titleLabel.setForeground(palette.onSurface());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        String[] professions = {"牙医", "医生", "公务员", "总裁", "教师"};
        comboBox = new JComboBox<>(professions);
        styleComboBox(comboBox);

        JPanel comboWrapper = new JPanel(new GridLayout(1, 1));
        comboWrapper.setOpaque(false); // 透明，透出背景
        comboWrapper.add(comboBox);

        comboWrapper.setPreferredSize(new Dimension(200, 40));
        contentPanel.add(comboWrapper, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton btnConfirm = new MDbutton("确定");


        btnConfirm.addActionListener(e -> {
            isConfirmed = true;
            selectedValue = (String) comboBox.getSelectedItem();
            dispose(); // 关闭窗口
        });


        buttonPanel.add(btnConfirm);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);


        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    /**
     * 获取用户选择的数据
     * @return 选中的职业，如果取消则返回 null
     */
    public String showDialog() {
        setVisible(true); // 这里会阻塞，直到 dispose()
        return isConfirmed ? selectedValue : null;
    }

    // --- 样式辅助方法 ---

    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        box.setBackground(palette.surface());
        box.setForeground(palette.onSurface());
        ((JComponent) box.getRenderer()).setBorder(new EmptyBorder(5, 10, 5, 10));
    }


    // --- 测试入口 ---
    public static void main(String[] args) {

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        JFrame frame = new JFrame("主游戏窗口");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton button = new JButton("开始游戏 (弹出选择)");
        JLabel resultLabel = new JLabel("未选择");

        button.addActionListener(e -> {

            SelectDialog dialog = new SelectDialog(frame);
            String result = dialog.showDialog();

            if (result != null) {
                resultLabel.setText("你选择了: " + result);
                resultLabel.setForeground(new Color(0, 100, 0));
            } else {
                resultLabel.setText("用户取消了选择");
                resultLabel.setForeground(Color.RED);
            }
        });

        frame.add(button);
        frame.add(resultLabel);
        frame.setVisible(true);
    }
}