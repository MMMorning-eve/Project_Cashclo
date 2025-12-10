package top.liewyoung.view;

import javax.swing.*;
import java.awt.*;

public class Stater {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.add(new MainMap(), BorderLayout.CENTER);
        frame.add(new DashboardPanel(), BorderLayout.EAST);
        frame.setSize(1000, 650);

        frame.setResizable(false);
        frame.setVisible(true);
    }
}
