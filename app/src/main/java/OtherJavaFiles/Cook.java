package OtherJavaFiles;
import android.graphics.Bitmap;

public class Cook extends User{
    //private String firstName, lastName, email, password;
    private Bitmap blankCheque;
    private String description;

    public Cook(String firstName,String lastName,String email,String password,String address, String description){
        super(firstName,lastName,email,password,address);
        this.blankCheque = null;
        this.description = description;
    }


    public Bitmap getBlankCheque() {
        return this.blankCheque;
    }

    public void setBlankCheque(Bitmap blankCheque) {
        this.blankCheque = blankCheque;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if(input == null || input.isEmpty()){
            throw new IllegalArgumentException("password is invalid");
        }
        this.description = description;
    }

}
