public class User {
    int ID;
    int Rank;
    int Warning;
    String Status;
    String UserName;
    String Password;

    public User(int ID, int rank, int warning, String status, String userName, String password) {
        this.ID = ID;
        Rank = rank;
        Warning = warning;
        Status = status;
        UserName = userName;
        Password = password;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public void setWarning(int warning) {
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

    public int getID() {
        return ID;
    }

    public int getRank() {
        return Rank;
    }

    public int getWarning() {
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
