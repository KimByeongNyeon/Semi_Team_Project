package semiTeamProject.Model;

public class UserVO {
    private String userId;
    private String password;
    private boolean isAdmin;

    public UserVO(String userId, String password, boolean isAdmin) {
        this.userId = userId;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

