package lab2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created on 14.10.19
 */
public class Shop {
    private Semaphore semaphore;
    private Semaphore queueSemaphore = new BinarySemaphor();
    private Queue<Cart> carts = new LinkedList<>();

    public Shop() {
        int numberOfCarts = 3;
        semaphore = new CountingSemaphore(numberOfCarts);
        for (int i = 0; i < numberOfCarts; i++) {
            carts.add(new Cart("Cart " + Integer.toString(i + 1)));
        }
    }

    public Cart takeCart() {
        semaphore.P();
        queueSemaphore.P();
        Cart cart = carts.element();
        carts.remove();
        queueSemaphore.V();
        return cart;
    }

    public void returnCart(Cart cart) {
        queueSemaphore.P();
        carts.add(cart);
        queueSemaphore.V();
        semaphore.V();
    }
}
