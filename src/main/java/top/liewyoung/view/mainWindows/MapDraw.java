package top.liewyoung.view.mainWindows;

import top.liewyoung.strategy.TitlesTypes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * 我们进入全新的Mater Design时代
 *
 * @author LiewYoung
 * @since 2025/12/13
 */
public class MapDraw extends JPanel {
    private static final int MAP_HEIGHT = 8;
    private static final int MAP_WIDTH = 8;
    private static final int MARGIN = 30;
    private static final int SPACING = 10;
    private List<Tile> tiles;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // 开启抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }

    public void calculateTiles() {
        Random r = new Random();
        for (int i = 0; i < MAP_WIDTH; i++) {
            int index;
            if (i == 1) {
                index = 1;
            } else {
                index = r.nextInt(1, 6);
            }
            TitlesTypes type = TitlesTypes.values()[index];
            Tile tile = new Tile(i, 0, type);
            tiles.add(tile);
        }

        for (int i = 1; i < MAP_HEIGHT-2; i++) {
            int index=r.nextInt(1,6);
            Tile tile = new Tile(0, i, TitlesTypes.values()[index]);
            tiles.add(tile);
        }

        for (int i = 2; i < MAP_WIDTH-2; i++) {
            int index=r.nextInt(1,6);
            Tile tile = new Tile(i, MAP_HEIGHT-2, TitlesTypes.values()[index]);
            tiles.add(tile);
        }

        for (int i = 2; i < MAP_WIDTH-2; i++) {
            int index=r.nextInt(1,6);
            Tile tile = new Tile(MAP_WIDTH-1, i, TitlesTypes.values()[index]);
            tiles.add(tile);
        }


    }


    class PlayerPosition {
        int x;
        int y;

        public PlayerPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class Tile {
        int x;
        int y;
        TitlesTypes type;

        public Tile(int x, int y, TitlesTypes type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }
}
