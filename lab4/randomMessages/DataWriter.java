package lab4.randomMessages;

import java.io.FileWriter;

public class DataWriter {
    private FileWriter csvWriter;

    public DataWriter(String fileName) {
        try {
            csvWriter = new FileWriter(fileName);
        } catch (Exception e) {
            System.out.println("Data writer");
        }
    }

    public void write(Data data) {
        try {
            csvWriter.append(String.valueOf(data.size)).append(",").append(Long.toString(data.time)).append("\n");
        } catch (Exception e) {
            System.out.println("Data writer write");
        }
    }

    public void save() {
        try {
            csvWriter.flush();
        } catch (Exception e) {
            System.out.println("Data writer flush");
        }
    }
}
