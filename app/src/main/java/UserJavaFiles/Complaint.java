package UserJavaFiles;

public class Complaint {
    private String email;
    private String complaint;
    private String id;
    public Complaint(){}

    public Complaint(String email, String complaint){
        this.email = email;
        this.complaint = complaint;

    }
    public Complaint(String email, String complaint,String id){
        this.email = email;
        this.complaint = complaint;
        this.id = id;

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public boolean equals(Complaint complaintAgainst){
        if(complaintAgainst.getId().equals(this.getId())){
            return true;
        }
        else {
            return false;
        }
    }
}
