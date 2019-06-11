package sample;

public class User {
    String ID;
    String Rank;
    String Warning;
    String Status;
    String UserName;
    String Password;

    public User(String ID, String rank, String warning, String status, String userName, String password) {
        this.ID = ID;
        Rank = rank;
        Warning = warning;
        Status = status;
        UserName = userName;
        Password = password;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public void setWarning(String warning) {
        Warning = warning;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void ChangePass(String password) {
        Password = password;
    }

    public String getID() {
        return ID;
    }

    public String getRank() {
        return Rank;
    }

    public String getWarning() {
        return Warning;
    }

    public String getStatus() {
        return Status;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }
}
