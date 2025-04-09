package beerThread;

import javax.swing.*;

public class MovementThread extends Thread{
    private BeerGlass beerGlass;

    public MovementThread (BeerGlass beerGlass) {
        this.beerGlass = beerGlass;
    }
    public void run() {
        while (true) {
            beerGlass.getLiquid().fillIn();
            beerGlass.repaint();
            try {
                Thread.sleep(15);
            }
            catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }
}
