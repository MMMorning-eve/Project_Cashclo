package top.liewyoung.view;

import top.liewyoung.view.mainWindows.DashboardPanel;

import javax.swing.*;
import java.awt.*;

public class Stater {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.add(new MainMap(), BorderLayout.WEST);
        frame.add(new DashboardPanel()  , BorderLayout.EAST);
        frame.setSize(1000, 735);

        frame.setResizable(false);
        frame.setVisible(true);
    }
}
