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
        String role;
        if (this.role.equals("sa")) {
            role = "Admin";
        } else {
            role = "Thu kho";
        }

        System.out.printf("%-20s%-20s%-20s%-20s\n", "STT", "Ten nguoi dung", "Tai khoan", "Quyen");
        System.out.printf("%-20s%-20s%-20s%-20s\n", this.ten, "Ten nguoi dung", "Tai khoan", "Quyen");
        System.out.println("Ten nguoi dung: " + this.ten);
        System.out.println("Tai khoan: " + this.user_name);
        System.out.println("Mat khau: " + this.password);
        System.out.println("Quyen : " + role);
        System.out.println("------------------------");
    }
}
