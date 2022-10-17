package UserJavaFiles;

import android.graphics.Bitmap;

/**
 * this class is SPECIFICALLY meant for firebase integration, a very generic POJO class that
 * Implements a skeleton of the user, this data will be sent to the database and retrieved
 * in this form and structure, later the data in this class will be distrubuited to the client
 * cook or admin class depending on the type variable
 */
public class UserPOJO {
    /**
     * takes any and all fields from user to the subclasses in terms of variables
     * while the user class will make sure the basics of firstname - address are not null
     * things like credit card info and blank cheque can be null depending on what data the
     * pojo is given (client or cook or admin type)
     */
    private String firstName, lastName, email, password, address,type;
    private CreditCard creditCardInfo;
    private Bitmap blankCheque;

    public UserPOJO(String firstName,String lastName,String email,String password,String address,String type,CreditCard creditCardInfo, Bitmap blankCheque){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.type = type;
        this.creditCardInfo = creditCardInfo;
        this.blankCheque = blankCheque;
    }
    //must have a blank constructor for firebase use
    public  UserPOJO(){}


    //getters

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public CreditCard getCreditCardInfo() {
        return creditCardInfo;
    }

    public Bitmap getBlankCheque() {
        return blankCheque;
    }

    //setters

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreditCardInfo(CreditCard creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    public void setBlankCheque(Bitmap blankCheque) {
        this.blankCheque = blankCheque;
    }
}
