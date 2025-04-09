package beerThread;

import java.awt.*;
import java.util.ArrayList;

public class Liquid {
    private Rectangle liquid;
    private BeerGlass beerGlass;
    private int heightIncrement;
    public Liquid(int x, int y, int width, int height, BeerGlass beerGlass) {
        this.liquid = new Rectangle(x, y, width, height);
        heightIncrement = 1;
        this.beerGlass = beerGlass;
    }
    public void draw(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(liquid.x, liquid.y, liquid.width, liquid.height);
    }
    public void fillIn() {
        liquid.height -= heightIncrement;
        if (liquid.height == BeerGlass.getMaxLiquidHeight() || liquid.intersects(beerGlass.getHorizontalsBeerGlassBorders().getFirst().rectangle)) {
                heightIncrement *= -1;
        }
    }
}
