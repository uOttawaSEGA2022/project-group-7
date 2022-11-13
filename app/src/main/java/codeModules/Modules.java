package codeModules;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Cook;
import UserJavaFiles.User;

/**
 * basic class that holds code modules that are reused throughout the project for ease
 * of use and access
 */
public class Modules extends AppCompatActivity {
    public Modules(){}

    /**
     * simply allows the page to catch the transfering of users, used almost everywhere
     * so modulrized here
     * @param intent the application intent sent to the page that contains the user
     * @return returns the user and what it is specifically i.e cook admin client
     */
    public User catchUser(Intent intent){
        User user = null;
        try{
            user = (Client) intent.getSerializableExtra("Client");
            user.getFirstName();
        }catch (Exception e){
            System.out.println("error here1 " + e);
            try{
                user = (Cook) intent.getSerializableExtra("Cook");
                user.getFirstName();
            }catch (Exception g){
                System.out.println("error here2 " + e);
                try{
                    user = (Administrator) intent.getSerializableExtra("Admin");
                    user.getFirstName();
                }catch (Exception h){
                    System.out.println("error here3 " + e);
                }
            }
        }
        return user;
    }
}
