package UserJavaFiles;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreditCard implements Serializable {
    //instance variables
    private String firstName, lastName, address;
    private long number;
    private int pin;
    private String expirationDate;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");

    /**
     * Constructs a credit card object with everything accounted for, even throws a exceptions
     * for illegal arguments used for future usage
     * @param firstName cannot be null
     * @param lastName cannot be null
     * @param address cannot be null
     * @param number had to be betwen 8 and 19 digits long
     * @param pin has to be 3 digits
     * @param expirationDate checked seperatley, see helper method checkExpDate
     * @throws ParseException thrown when the expirationdate format is wrong
     * @throws Exception thrown when expiration date is faulty (too old)
     */
    public CreditCard(String firstName,String lastName,String address, long number, int pin, String expirationDate) throws ParseException,Exception {
        if(firstName == null || firstName.isEmpty()){
            throw new IllegalArgumentException("First name is invalid");
        }else if(lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("Last name is invalid");
        }else if(address == null || address.isEmpty()){
            throw new IllegalArgumentException("Address is invalid");
        }else if(String.valueOf(number).length()>19 || String.valueOf(number).length()<8){
            throw new IllegalArgumentException("Credit card number is either too long or too short");
        }else if(String.valueOf(pin).length() != 3){
            throw new IllegalArgumentException("PIN is invalid");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.number = number;
        this.pin = pin;
        checkExpDate(expirationDate);
        this.expirationDate = String.valueOf(dateFormat.parse(expirationDate));
    }
    //empty constructor for firebase
    public CreditCard(){}
    /**
     * helper method for constructor to check the validity of the suspension date
     * @param expirationDate when the user is banned until
     * @throws Exception throws this exception if it cannot parse properly or if date is older
     * than current date
     */
    private void checkExpDate( String expirationDate) throws Exception{
        Date currentDate = new Date();

        Date expire = (dateFormat.parse(expirationDate));

        //check if already expired
        if(currentDate.after(expire)){
            throw new Exception();
        }
    }

    /**
     * getters and setter follow, keep in mind that these getters and setters are
     * strictly for firebase usage and not for usage by the class, one should not be able
     * to change their credit card number pin etc once created, logically they would
     * have to create a new credit card object instead!
     */
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {this.address = address;}
    public long getNumber() {
        return this.number;
    }
    public void setNumber(long number) {this.number = number;}
    public int getPin() {
        return this.pin;
    }
    public void setPin(int pin) {this.pin = pin;}
    public String getExpirationDate() {
        return this.expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    //equals to method
    public boolean equalsTo(CreditCard cc){
        return this.firstName == cc.getFirstName() && this.lastName == cc.getLastName() && this.address  == cc.getAddress()
                && this.number == cc.getNumber() && this.pin == cc.getPin() && this.expirationDate == cc.getExpirationDate();
    }
}