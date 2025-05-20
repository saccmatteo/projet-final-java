package beerThread;

import java.awt.*;

public class Liquid {
    private Rectangle liquid;
    private BeerGlass beerGlass;
    private static int heightIncrement = 1;

    // CONSTRUCTOR
    public Liquid(int x, int y, int width, int height, BeerGlass beerGlass) {
        this.liquid = new Rectangle(x, y, width, height);
        this.beerGlass = beerGlass;
    }

    // METHODES
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(liquid.x, liquid.y, liquid.width, liquid.height);
    }

    public void fillIn() { // Remplir la bière
        liquid.height -= heightIncrement;
        if (liquid.height == BeerGlass.getMaxLiquidHeight() || liquid.intersects(beerGlass.getHorizontalsBeerGlassBorders().getFirst().rectangle)) {
                heightIncrement = -heightIncrement;
        }
    }
}
