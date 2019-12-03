package lab8;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class FilePreparer {
    private String load(String fileName) throws IOException {
        return new Scanner(new File(fileName)).useDelimiter("\\Z").next();
    }

    private String prepare(String content) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int ratio = content.length() / 100;
        for (int i = 0; i < 100; i++) {
            int pos = random.nextInt(ratio);
            stringBuilder.append(content, i * ratio, i * ratio + pos).append("  ").append(content, i * ratio + pos, (i + 1) * ratio);
        }
        return stringBuilder.toString();
    }

    public String loadAndPrepare(String fileName) throws IOException {
        return prepare(load(fileName));
    }
}

