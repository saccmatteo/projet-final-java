package beerThread;

import javax.swing.*;

public class MovementThread extends Thread{
    private BeerGlass beerGlass;

    public MovementThread (BeerGlass beerGlass) {
        this.beerGlass = beerGlass;
    }

    public void run() {
        while (true) {
            try {
                beerGlass.getLiquid().fillIn();
                beerGlass.repaint();
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }
}
