package UserJavaFiles;


import java.io.Serializable;

/**
 * abstraction for the user, contains the basics in the first and last name, email
 * password and address, what every user needs in their profile to function
 */
public abstract class User implements Serializable {
    private String firstName, lastName, email, password, address;
    //constructor for User, includes attributes firstName, lastName, email, password, address and checks if the input is correct.
    public User(String firstName,String lastName,String email,String password,String address) throws IllegalArgumentException{
        if(checkFirstName(firstName) && checkLastName(lastName) && checkEmail(email) && checkPassword(password) && checkAddress(address)){
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.address = address;
        }else{
            throw new IllegalArgumentException("Invalid parameter input");
        }
    }
    //getter and setter methods
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getAddress(){
        return address;
    }

    //For setter methods, check if input is valid first
    public void setFirstName(String input){
        if(checkFirstName(input) == false){
            throw new IllegalArgumentException("firstName is invalid");
        }
        firstName = input;
    }

    public void setLastName(String input){
        if(checkLastName(input) == false){
            throw new IllegalArgumentException("lastName is invalid");
        }
        lastName = input;
        
    }

    public void setEmail(String input){
        if(checkEmail(input) == false){
            throw new IllegalArgumentException("email is invalid");
        }    
        email = input;
            
    }

    public void setPassword(String input){
        if(checkPassword(input) == false){
            throw new IllegalArgumentException("password is invalid");
        }
        password = input;
    }

    public void setAddress(String input){
        if(checkAddress(input) == false){
            throw new IllegalArgumentException("address is invalid");
        }
        address = input;
            
    }

    //method for checking the firstName input. 
    private boolean checkFirstName(String input){
        //check if input is null or blank
        boolean isValid = true;
        if(input == null || input.isEmpty()){
           isValid = false;
        }
        return isValid;
    }

    //method for checking the lastName input. 
    private boolean checkLastName(String input){
        //check if input is null or blank
        boolean isValid = true;
        if(input == null || input.isEmpty()){
           isValid = false;
        }
        return isValid;
    }

    //method for checking the email input. 
    private boolean checkEmail(String input){
        //check if input is null or blank
        boolean isValid = true;
        if(input == null || input.isEmpty()){
           isValid = false;
        }
        return isValid;
    }

    //method for checking the password input. 
    private boolean checkPassword(String input){
        //check if input is null or blank
        boolean isValid = true;
        if(input == null || input.isEmpty()){
           isValid = false;
        }
        return isValid;
    }

    //method for checking the address input. 
    private boolean checkAddress(String input){
        //check if input is null or blank
        boolean isValid = true;
        if(input == null || input.isEmpty()){
           isValid = false;
        }
        return isValid;
    }
}
