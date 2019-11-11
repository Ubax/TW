package lab5;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.concurrent.*;

/**
 * Created on 28.10.19
 */
public class Mandelbrot {

    private int chunkWidth;
    private int chunkHeight;
    private ImageObserver observer;
    private int numberOfXChunk;
    private int numberOfYChunk;
    Object[][] images;


    public Mandelbrot(int numberOfXChunk, int numberOfYChunk, int width, int height, int numberOfThreads, ImageObserver observer, int maxIterations, double zoom) {
        chunkWidth = width / numberOfXChunk;
        chunkHeight = height / numberOfYChunk;
        this.observer = observer;
        this.numberOfXChunk = numberOfXChunk;
        this.numberOfYChunk = numberOfYChunk;

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CompletionService<ChunkResult> completionService = new ExecutorCompletionService<>(executor);

        this.images = new Object[numberOfXChunk][numberOfYChunk];
        long start = System.nanoTime();
        for (int x = 0; x < numberOfXChunk; x++) {
            for (int y = 0; y < numberOfYChunk; y++) {
                completionService.submit(new Chunk(x, y, chunkWidth, chunkHeight, zoom, maxIterations));
            }
        }

        for (int x = 0; x < numberOfXChunk; x++) {
            for (int y = 0; y < numberOfYChunk; y++) {
                try {
                    ChunkResult result = completionService.take().get();
                    images[result.getX()][result.getY()] = result.getBufferedImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.nanoTime();

        System.out.print(end - start);
        System.out.print("\t");
    }

    public void draw(Graphics graphics) {
        for (int x = 0; x < numberOfXChunk; x++) {
            for (int y = 0; y < numberOfYChunk; y++) {
                try {
                    graphics.drawImage((BufferedImage) images[x][y], x * chunkWidth, y * chunkHeight, observer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
