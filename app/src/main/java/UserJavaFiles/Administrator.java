package UserJavaFiles;

public class Administrator extends User{
    public Administrator(String firstName,String lastName,String email,String password,String address) throws IllegalArgumentException{
        super(firstName,lastName,email,password,address);
    }
}
