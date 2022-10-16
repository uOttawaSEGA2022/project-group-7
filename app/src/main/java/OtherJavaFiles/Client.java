package OtherJavaFiles;
import java.io.IOException;
import java.time.MonthDay;

public class Client extends User{
    private CreditCard creditCardInfo;

    //Creates Client from Super Class User, adds creditCardInfo attribute
    public Client(String firstName,String lastName,String email,String password,String address, CreditCard creditCardInfo){
        super(firstName,lastName,email,password,address);
        this.creditCardInfo = creditCardInfo;
    }

    //getter and setter methods
    public CreditCard getCreditCardInfo() {
        return this.creditCardInfo;
    }

    public void setCreditCardInfo(CreditCard creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }
}