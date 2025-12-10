package top.liewyoung.view;

import javax.swing.*;
import java.awt.*;

public class MainMap extends JPanel {
    private final int reactWidth = 50;
    private final int reactHeight = 50;
    private final int spaceWidth = 10;
    private final int spaceHeight = 10;
    private final int topSpacing = 35;
    private final int leftSpacing = 35;

    public MainMap() {
        setBackground(Color.WHITE);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());


        g.setColor(Color.blue);
        for (int i = 0; i < 9; i++) {
            g.fillRect(i * (reactWidth + spaceWidth) + leftSpacing, topSpacing, reactWidth, reactHeight);
        }

        int num = (525 - topSpacing - reactHeight - spaceHeight) / 60;

        for (int i = 0; i < num; i++) {
            int y = i * (spaceHeight + reactHeight) + topSpacing + reactHeight + spaceHeight;
            g.fillRect(leftSpacing, y, reactWidth, reactHeight);
            g.fillRect(8 * (reactWidth + spaceWidth) + leftSpacing, y, reactWidth, reactHeight);
        }

        for (int i = 0; i < 9; i++) {
            g.fillRect(i * (reactWidth + spaceWidth) + leftSpacing, 515, reactWidth, reactHeight);
        }
    }
}


