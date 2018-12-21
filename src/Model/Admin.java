package Model;

public class Admin {

    private int id;
    private String ten;
    private String user_name;
    private String password;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String userName) {
        this.user_name = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void displayInfo() {
        System.out.println("------------------------");
        System.out.println("Tên người dùng: " + this.ten);
        System.out.println("Tài khoản đăng nhập: " + this.user_name);
        System.out.println("Mật khẩu: " + this.password);
        System.out.println("Quyền : " + this.role);
        System.out.println("------------------------");
    }
}
