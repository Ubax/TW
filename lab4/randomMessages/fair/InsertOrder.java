package lab4.randomMessages.fair;

/**
 * Created on 14.11.19
 */
public class InsertOrder extends Order {
    private String prefix;

    public InsertOrder(int numberOfElements, String prefix) {
        super(numberOfElements);
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
