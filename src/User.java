import java.io.Serializable;

/**
 * Created by user on 13.09.2017.
 */
public class User implements Serializable{
    private String userName;
    private String userPick;
    private String message;

    public User(String userName, String userPick) {
        this.userName = userName;
        this.userPick = userPick;

    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPick() {
        return userPick;
    }

    public void setUserPick(String userPick) {
        this.userPick = userPick;
    }
}
