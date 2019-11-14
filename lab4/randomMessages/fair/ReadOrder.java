package lab4.randomMessages.fair;

import java.util.ArrayList;

/**
 * Created on 14.11.19
 */
public class ReadOrder extends Order {
    private ArrayList<String> data;

    public ReadOrder(int numberOfElements) {
        super(numberOfElements);
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
