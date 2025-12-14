package top.liewyoung.view.component;

import top.liewyoung.view.ColorSystem.MaterialPalette;

import javax.swing.*;
import java.awt.*;


/**
 * Material Design 按钮
 * @author LiewYoung
 * @since 2025/12/14
 */
public class MDbutton extends JButton {
    private MaterialPalette palette = MaterialPalette.MOSS;

    public MDbutton(String text) {
        super(text);
        setPreferredSize(new Dimension(100, 40));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFont(new Font("微软雅黑", Font.BOLD, 12));
        setForeground(palette.onPrimary());
    }
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

        super.paintComponent(g);
    }
}
