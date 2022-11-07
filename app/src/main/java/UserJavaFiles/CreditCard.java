package UserJavaFiles;

import java.io.Serializable;
import java.util.Date;

public class CreditCard implements Serializable {
    private String firstName, lastName, address;
    private long number;
    private int pin;
    private Date expirationDate;

    public CreditCard(String firstName,String lastName,String address, long number, int pin, Date expirationDate) throws IllegalArgumentException{
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
        this.expirationDate = expirationDate;
        
    }
    //empty constructor for firebase
    public CreditCard(){

    }
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName == null || firstName.isEmpty()){
            throw new IllegalArgumentException("First name is invalid");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        if(lastName == null || lastName.isEmpty()){
            throw new IllegalArgumentException("Last name is invalid");
        }
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        if(address == null || address.isEmpty()){
            throw new IllegalArgumentException("Address is invalid");
        }
        this.address = address;
    }

    public long getNumber() {
        return this.number;
    }

    public void setNumber(long number) {
        if(String.valueOf(number).length()>19 || String.valueOf(number).length()<8){
            throw new IllegalArgumentException("Credit card number is either too long or too short");
        }
        this.number = number;
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int pin) {
        if(String.valueOf(pin).length() != 3){
            throw new IllegalArgumentException("PIN is invalid");
        }
        this.pin = pin;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean equalsTo(CreditCard cc){
        return this.firstName == cc.getFirstName() && this.lastName == cc.getLastName() && this.address  == cc.getAddress()
                && this.number == cc.getNumber() && this.pin == cc.getPin() && this.expirationDate == cc.getExpirationDate();
    }
}