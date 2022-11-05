package UserJavaFiles;

import java.io.Serializable;

public class Administrator extends User {
    public Administrator(String firstName,String lastName,String email,String password,String address) throws IllegalArgumentException{
        super(firstName,lastName,email,password,address);
    }

    /**
     * This method bans a Cook either permanently or until a specified date
     * When a Cook is banned, their 'status' attribute points to an object of type Suspension
     * Objects of type suspension specify if the ban is permanent or if it is time-set
     *
     * @param c Cook that is going to be banned
     * @param s Suspension object that wil determine the ban of the Cook
     */
    public void suspendCook(Cook c, Suspension s)
    {
        //suspend user
        c.setSuspension(s);

        //If ban is not permanent, Cook should be unbanned when (un)ban date is reached
        if(!s.getPerma())
        {
            //when ban date is reached
            //remove suspension
            c.setSuspension(null);
        }

    }
}
