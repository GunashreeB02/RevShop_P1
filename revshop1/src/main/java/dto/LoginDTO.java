package dto;

public class LoginDTO {

    private int userId;
    private String role;
    private String password;
    private boolean success;
    private String username;


    public LoginDTO()
    {

    }

    public LoginDTO(int userId, String role,String password, boolean success) {
        this.userId = userId;
        this.role = role;
        this.password = password;
        this.success = success;
    }


    public LoginDTO(int userId, String role,String password, boolean success,String username) {
        this.userId = userId;
        this.role = role;
        this.password = password;
        this.success = success;
        this.username=username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
