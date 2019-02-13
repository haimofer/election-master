package cropx.election;

/**
 * Created by Ofer on 2/13/2019.
 */
public class UserInfo {
    private int userId;
    private String pass;
    private int vote;

    public UserInfo() {
    }

    public UserInfo(int userId, String pass, int vote) {
        this.userId = userId;
        this.pass = pass;
        this.vote = vote;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "userId: " + userId + "\n pass: " + pass + "\n vote: " + vote;
    }
}
