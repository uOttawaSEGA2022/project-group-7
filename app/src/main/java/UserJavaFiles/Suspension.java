package UserJavaFiles;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Suspension implements Serializable {
    private boolean perma;
    private Date ban;
    private String bannedUntil;
    //This is a way to change the date variable into string for firebase implementation
    private SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Suspension(boolean perma, Date ban)
    {
        this.perma = perma;

        //changes the date into string
        if(perma == true){
            ban = null;
        }
        //means they are fine so set their status as OKAY
        else{
            try{
                this.bannedUntil = ISO_8601_FORMAT.format(ban);
            }catch(Exception e){
                this.bannedUntil = "OKAY";
            }
        }


    }
    //constructs with banned until instead of date
    public Suspension(boolean perma, String bannedUntil) throws ParseException, Exception {
        Exception exception;
        this.perma = perma;
        checkSuspension(bannedUntil);
        this.bannedUntil = String.valueOf(ISO_8601_FORMAT.parse(bannedUntil));

        this.ban = null;


    }
    public Suspension(){}

    private void checkSuspension( String bannedUntil) throws Exception{
        Date currentDate = new Date();
        Date suspensionDate = (ISO_8601_FORMAT.parse(bannedUntil));
        System.out.println("please");
        if(currentDate.after(suspensionDate)){

            throw new Exception();
        }
    }
    public String getBannedUntil() {
        return this.bannedUntil;
    }



    public  Boolean getPerma(){
        return perma;
    }
    public void setPerma(boolean perma) {
        this.perma = perma;
    }
    public void setBannedUntil(String bannedUntil) {
        this.bannedUntil = bannedUntil;
    }
}
