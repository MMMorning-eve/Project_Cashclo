package top.liewyoung.view.mainWindows;

import top.liewyoung.view.ColorSystem.MaterialPalette;
import top.liewyoung.view.Stater;
import top.liewyoung.view.component.CircleImageLabel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 关于界面
 * @author LiewYoung
 * @since 2025/12/14
 */
public class Setting extends JPanel {
    private MaterialPalette palette = MaterialPalette.MOSS;

    public Setting() {
        setLayout(new GridBagLayout());
        setBackground(palette.surface());
        setPreferredSize(new Dimension(1000, 600));

        JPanel listPanel = new JPanel( );
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        JLabel title = new JLabel("关于");
        title.setFont(new Font("微软雅黑", Font.BOLD, 24));
        title.setForeground(palette.onSurface());
        title.setBackground(palette.surface());
        listPanel.add(title);

        ImageIcon icon = new ImageIcon("src/main/resources/Avstar.jpg");
        listPanel.add(cardFactory("LiewYoung", "North China University of Water Resources and Electric Power", icon));

        ImageIcon imageIcon = new ImageIcon("src/main/resources/Avstar_1.jpg");
        listPanel.add(cardFactory("刘瑞翔", "North China University of Water Resources and Electric Power", imageIcon));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton buttonRest = new JButton("刷新元素"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(getModel().isPressed()){
                    g2d.setColor(palette.primary().darker());
                }else{
                    g2d.setColor(palette.primary());
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        buttonRest.setPreferredSize(new Dimension(100, 40));
        buttonRest.setFont(new Font("微软雅黑", Font.BOLD, 14));
        buttonRest.setForeground(palette.onPrimary());
        buttonRest.setContentAreaFilled(false);
        buttonRest.setBorderPainted(false);
        buttonRest.setFocusPainted(false);
        buttonRest.addActionListener(e -> {
            Stater.map.refreshMap();
        });
        buttonPanel.add(buttonRest);

        listPanel.add(buttonPanel);

        add(listPanel);
    }

    private JPanel cardFactory(String name , String desc,ImageIcon icon) {
        CircleImageLabel circleImageLabel = new CircleImageLabel(icon, 100);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(palette.surface());

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(nameLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descLabel);

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT,25,0));
        p.setBackground(palette.surface());
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        p.add(circleImageLabel);
        p.add(textPanel);

        return p;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("关于");

        frame.add(new Setting());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}