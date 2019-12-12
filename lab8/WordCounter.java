package lab8;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCounter {
    public long countWords(String text) {
        return Stream.of(text.split("\\s+")).count();
    }

    public long countWordsParallel(String text) {
        return Stream.of(text.split("\\s+")).parallel().count();
    }

    public long countWordsParallelSpliterator(String text) {
        return StreamSupport.stream(new CountSpliterator(text), true).parallel().reduce(new ReduceState(), (ReduceState state, Character c) -> {
            if (Character.isWhitespace(c)) {
                if (!state.isPrevSpace()) {
                    state.markSpace();
                    state.inc();
                }
            } else state.unmarkSpace();
            return state;
        }, ReduceState::merge).getCount();
    }

    private class CountSpliterator implements Spliterator<Character> {
        private int currentIndex;
        private String text;

        public CountSpliterator(String text) {
            this.text = text;
            this.currentIndex = 0;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Character> consumer) {
            consumer.accept(text.charAt(this.currentIndex));
            this.currentIndex++;
            return this.currentIndex < this.text.length();
        }

        @Override
        public Spliterator<Character> trySplit() {
            int size = text.length() - currentIndex;
            if (size < 100) return null;
            int d = size / 2;
            for (int i = d + currentIndex; i < text.length(); i++) {
                if (Character.isWhitespace(text.charAt(i))) {
                    Spliterator<Character> spliterator = new CountSpliterator(text.substring(currentIndex, i));
                    currentIndex = i;
                    return spliterator;
                }
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return this.text.length() - currentIndex;
        }

        @Override
        public int characteristics() {
            return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
        }
    }
}
