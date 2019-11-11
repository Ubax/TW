package lab5;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

/**
 * Created on 28.10.19
 */
public class Chunk implements Callable<ChunkResult> {
    private int x, y, width, height;
    private double zoom;
    private int maxIterations;

    public Chunk(int x, int y, int width, int height, double zoom, int maxIterations) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.maxIterations = maxIterations;
    }

    @Override
    public ChunkResult call() throws Exception {
        double zx, zy, cX, cY, tmp;
        int _x=this.x*this.width, _y=this.y*this.height;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = _y; y < _y + height; y++) {
            for (int x = _x; x < _x + width; x++) {
                zx = zy = 0;
                cX = (x - 400) / this.zoom;
                cY = (y - 300) / this.zoom;
                int iter = this.maxIterations;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }

                image.setRGB(x - _x, y - _y, iter | (iter << 8));
            }
        }
        return new ChunkResult(image, this.x, this.y);
    }
}
