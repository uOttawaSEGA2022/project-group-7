package UserJavaFiles;
import android.graphics.Bitmap;

public class Cook extends User{
    private Bitmap blankCheque;

    public Cook(String firstName,String lastName,String email,String password,String address){
        super(firstName,lastName,email,password,address);
        this.blankCheque = null;
    }


    public Bitmap getBlankCheque() {
        return this.blankCheque;
    }

    public void setBlankCheque(Bitmap blankCheque) {
        this.blankCheque = blankCheque;
    }

}
