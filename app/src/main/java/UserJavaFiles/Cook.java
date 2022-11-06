package UserJavaFiles;
import android.graphics.Bitmap;

public class Cook extends User{
    private Bitmap blankCheque;
    private String description;
    private Suspension status;
    public Cook(String firstName,String lastName,String email,String password,String address, String description,Suspension status){
        super(firstName,lastName,email,password,address);
        this.description = description;
        this.blankCheque = null;
        this.status = status;
    }

    //getters and setters
    public Bitmap getBlankCheque() {
        return this.blankCheque;
    }
    public String getDescription() {return description;}
    public Suspension getSuspension() {return status;}
    public void setBlankCheque(Bitmap blankCheque) {
        this.blankCheque = blankCheque;
    }
    public void setDescription(String description) {this.description = description;}
    public void setSuspension(Suspension suspend){this.status = suspend;}
}
