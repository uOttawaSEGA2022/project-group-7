package UserJavaFiles;

import java.io.Serializable;

public class Administrator extends User implements Serializable {
    public Administrator(String firstName,String lastName,String email,String password,String address) throws IllegalArgumentException{
        super(firstName,lastName,email,password,address);
    }
}
