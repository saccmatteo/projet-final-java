package beerThread;

import java.awt.*;

public class BeerGlassBorder {
    Rectangle rectangle;
    public BeerGlassBorder(int x, int y, int width, int height) {
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
