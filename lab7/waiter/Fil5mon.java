public class Fil5mon {
    public static void main(String[] args) {
        System.out.println("xd");
        int ilosc= 5;
        Filozof[] filozofs = new Filozof[ilosc];
        Widelec[] widelecs = new Widelec[ilosc];

        Waiter waiter = new Waiter();
        for (int i = 0; i<ilosc; i++){
            widelecs[i] = new Widelec();
        }
        for (int i=0; i<ilosc; i++){
            filozofs[i] = new Filozof(widelecs[i], widelecs[(i+1)%ilosc], i, waiter);
        }
        for (int i=0; i<ilosc; i++){
            filozofs[i].start();
        }
    }
}
