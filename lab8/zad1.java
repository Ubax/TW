package lab8;

public class zad1 {
    public static void main(String[] args) {
        try {
            FilePreparer filePreparer = new FilePreparer();
            String data = filePreparer.loadAndPrepare("pantadeusz.txt");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
