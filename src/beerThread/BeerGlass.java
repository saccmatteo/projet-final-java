package beerThread;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BeerGlass extends JPanel {
    private Liquid liquid;
    private ArrayList<BeerGlassBorder> horizontalsBeerGlassBorders = new ArrayList<>();
    private ArrayList<BeerGlassBorder> verticalsBeerGlassBorder = new ArrayList<>();
    private final static int MAX_LIQUID_HEIGHT = -380;

    public BeerGlass() {
        // Verre (2 côtés verticals et 1 côté horizontal)
        horizontalsBeerGlassBorders.add(new BeerGlassBorder(800, 500, 300, 10));
        verticalsBeerGlassBorder.add(new BeerGlassBorder(800, 100, 10, 400));
        verticalsBeerGlassBorder.add(new BeerGlassBorder(1090, 100, 10, 400));

        // Manche (1 côté vertical et 2 côtés horizontals)
        horizontalsBeerGlassBorders.add(new BeerGlassBorder(1100, 350, 60, 10));
        horizontalsBeerGlassBorders.add(new BeerGlassBorder(1100, 170, 60, 10));
        verticalsBeerGlassBorder.add(new BeerGlassBorder(1150, 170, 10, 180));

        // Liquide
        liquid = new Liquid(810, 500, 280, 0, this);

        // Thread
        MovementThread movementThread = new MovementThread(this);
        movementThread.start();
    }
    public void paint(Graphics g) {
        super.paint(g);
        for (BeerGlassBorder beerGlassBorder : horizontalsBeerGlassBorders) {
            beerGlassBorder.draw(g);
        }
        for (BeerGlassBorder beerGlassBorder : verticalsBeerGlassBorder) {
            beerGlassBorder.draw(g);
        }
        liquid.draw(g);
    }

    public static int getMaxLiquidHeight() {
        return MAX_LIQUID_HEIGHT;
    }

    public ArrayList<BeerGlassBorder> getHorizontalsBeerGlassBorders() {
        return horizontalsBeerGlassBorders;
    }

    public Liquid getLiquid() {
        return liquid;
    }
}
