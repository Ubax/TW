package lab5;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * Created on 28.10.19
 */
public class zad1 extends JFrame {
    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    private Mandelbrot mandelbrot;

    public zad1() {
        super("Mandelbrot Set");
        int width = 800;
        int height = 600;
        setBounds(100, 100, width, height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //System.out.println("1 thread");
        mandelbrot = new Mandelbrot(1, 1, width, height, 1, this, 800, 150);
        mandelbrot = new Mandelbrot(2, 5, width, height, 1, this, 800, 150);
        mandelbrot = new Mandelbrot(width, height, width, height, 1, this, 800, 150);
        //System.out.println("4 threads");
        mandelbrot = new Mandelbrot(2, 2, width, height, 4, this, 800, 150);
        mandelbrot = new Mandelbrot(4, 10, width, height, 4, this, 800, 150);
        mandelbrot = new Mandelbrot(width, height, width, height, 4, this, 800, 150);
        //System.out.println("8 thread");
        mandelbrot = new Mandelbrot(4, 2, width, height, 4, this, 800, 150);
        mandelbrot = new Mandelbrot(8, 10, width, height, 4, this, 800, 150);
        mandelbrot = new Mandelbrot(width, height, width, height, 4, this, 800, 150);
    }

    @Override
    public void paint(Graphics g) {
        mandelbrot.draw(g);
    }

    public static void main(String[] args) {
        new zad1().setVisible(true);
    }
}
