package lab2;

/**
 * Created on 14.10.19
 */
public class ShopConsumer implements Runnable{
    private Shop shop;
    private String prefix;

    public ShopConsumer(Shop shop, String prefix) {
        this.shop = shop;
        this.prefix = prefix;
    }

    @Override
    public void run() {
        for(int i=2;i>0;i--){
            try {
                shop.takeCart();
                System.out.println(this.prefix + " is in shop");
                Thread.sleep(2000);
                System.out.println(this.prefix + " got out");
                shop.returnCart();
            }catch (Exception e){
                System.out.println(this.prefix + " become injured");
            }
        }
    }
}
