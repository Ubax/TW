package lab8;

public class zad1 {
    public static void main(String[] args) {
        try {
            FilePreparer filePreparer = new FilePreparer();
            String data = filePreparer.loadAndPrepare("pantadeusz.txt");
            long start = System.nanoTime();
            long count = new WordCounter().countWords(data);
            long end = System.nanoTime();
            long startParallel = System.nanoTime();
            long countParallel = new WordCounter().countWordsParallel(data);
            long endParallel = System.nanoTime();
            long startParallelSplit = System.nanoTime();
            long countParallelSplit = new WordCounter().countWordsParallelSpliterator(data);
            long endParallelSplit = System.nanoTime();
            System.out.println("Number of words normal: " + count);
            System.out.println("Number of words parallel: " + countParallel);
            System.out.println("Number of words spliterator: " + countParallelSplit);
            System.out.println("Time normal: " + (end - start * 1.0) / 1_000_000 + "ms");
            System.out.println("Time parallel: " + (endParallel - startParallel * 1.0) / 1_000_000 + "ms");
            System.out.println("Time spliterator: " + (endParallelSplit - startParallelSplit * 1.0) / 1_000_000 + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
