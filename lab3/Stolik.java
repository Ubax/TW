package lab3;

/**
 * Created on 23.10.19
 */
public class Stolik {
    Para para = null;

    public boolean czyJestZajety() {
        return para != null;
    }

    public void setPara(Para para) {
        this.para = para;
    }

    public Para getPara() {
        return para;
    }

    public void zwolnij() {
        this.para = null;
    }
}
