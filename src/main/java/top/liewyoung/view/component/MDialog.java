package top.liewyoung.view.component;

import top.liewyoung.view.ColorSystem.MaterialPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * Material Design 3 风格对话框
 * @author LiewYoung
 * @since 2025/12/14
 */
public class MDialog extends JDialog {
    private MaterialPalette palette = MaterialPalette.MOSS;

    public MDialog(String content, String closeButtonText) {
        setBackground(palette.surface());
        setLayout(new BorderLayout());
        setSize(new Dimension(400, 200));

        Container contentPanel = getContentPane();
        contentPanel.setBackground(palette.surface());


        JPanel textPanel = new JPanel(new FlowLayout());
        textPanel.setBackground(palette.surface());
        textPanel.add(contentLabelFactory(content));
        textPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(palette.surface());
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        buttonPanel.add(buttonFactory(closeButtonText));

        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


    }

    protected JButton buttonFactory(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {


                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(palette.primary().darker());
                } else {
                    g2d.setColor(palette.primary());
                }

                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        button.setPreferredSize(new Dimension(100, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("微软雅黑", Font.BOLD, 12));
        button.setForeground(palette.onPrimary());

        button.addActionListener(e -> {
            dispose();
        });


        return button;
    }



    // 内容标签
    protected JLabel contentLabelFactory(String content) {
        JLabel label = new JLabel(content);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label.setForeground(palette.onSurface());

        return label;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MD3 Dialog");
        JButton button = new JButton("Open Dialog");
        button.addActionListener(e -> {
            MDialog dialog = new MDialog("Hello Java", "关闭");
            dialog.setVisible(true);
        });
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
