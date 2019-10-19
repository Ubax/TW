package lab2;

/**
 * Created on 14.10.19
 */
public class Cart {
    private Semaphore semaphore = new BinarySemaphor();
    private String name;

    public Cart(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}