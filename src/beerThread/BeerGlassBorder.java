package beerThread;

import java.awt.*;

public class BeerGlassBorder {
    Rectangle rectangle;

    // CONSTRUCTOR
    public BeerGlassBorder(int x, int y, int width, int height) {
        this.rectangle = new Rectangle(x, y, width, height);
    }

    // METHODES
    public void draw(Graphics g) {
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
