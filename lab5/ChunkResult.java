package lab5;

import java.awt.image.BufferedImage;

/**
 * Created on 28.10.19
 */
public class ChunkResult {
    private BufferedImage bufferedImage;
    private int x;
    private int y;

    public ChunkResult(BufferedImage bufferedImage, int x, int y) {
        this.bufferedImage = bufferedImage;
        this.x = x;
        this.y = y;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
