package UserJavaFiles;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;

public class Administrator extends User {
    public Administrator(String firstName,String lastName,String email,String password,String address) throws IllegalArgumentException{
        super(firstName,lastName,email,password,address);
    }

    /**
     * This method bans a Cook either permanently or until a specified date
     * When a Cook is banned, their 'status' attribute points to an object of type Suspension
     * Objects of type suspension specify if the ban is permanent or if it is time-set
     *
     * @param cook Cook that is going to be banned from the database
     * @param s Suspension object that wil determine the ban of the Cook
     */
    public void suspendCook(Suspension s, DatabaseReference cook)
    {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("suspension",s);
        //from within the database set the cooks status
        cook.updateChildren(map);

    }
}
