import top.liewyoung.view.mainWindows.DashboardPanel;

import javax.swing.*;

public class DashBoardTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new DashboardPanel());
        frame.setSize(335,735);
        frame.setVisible( true);
    }
}
