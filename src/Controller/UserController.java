package Controller;

import DataConnect.DataConnection;
import Model.Admin;
import Repository.AdminRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserController {

    Scanner sc = new Scanner(System.in);
//    DataConnection dataconn;
//    AdminRepository adRepository;
//    ArrayList<Admin> adList = new ArrayList<>();

    public void main() throws SQLException {

        Scanner in = new Scanner(System.in);
        boolean quit = false;
        int menuItem = -1;
        do {
            System.out.println("---------Quan ly nguoi dung---------");
            menu();
            System.out.print("Chon 1 chuc nang: ");
            boolean valid;
            do {
                try {
                    menuItem = Integer.parseInt(sc.nextLine());
                    valid = true;
                } catch (NumberFormatException ex) {
                    valid = false;
                    System.out.print("Vui long nhap so : ");
                }
            } while (valid == false);

            switch (menuItem) {
                case 1:
                    getAllUsers();
                    sc.nextLine();
                    break;
                case 2:
                    insertUser();
                    sc.nextLine();
                    break;
                case 3:
                    editUser();
                    sc.nextLine();
                    break;
                case 4:
                    searchByName();
                    sc.nextLine();
                    break;
                case 5:
                    deleteUser();
                    sc.nextLine();
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.print("Lua chon khong ton tai, vui long nhap lai : ");
            }
        } while (!quit);

    }

    public void menu() {
        System.out.println("1. Danh sach nguoi dung");
        System.out.println("2. Them moi nguoi dung");
        System.out.println("3. Sua thong tin nguoi dung");
        System.out.println("4. Tim kiem thong tin nguoi dung");
        System.out.println("5. Xoa nguoi dung");
        System.out.println("0. Tro lai");
    }

    public void getAllUsers() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> adList = adRepository.GetAll();

        if (adList.size() <= 0) {
            System.out.print("Hien tai chua co nguoi dung nao");
        } else {
            System.out.println("Danh sach nguoi dung : ");
            adList.forEach((user) -> {
                user.displayInfo();
            });
        }
    }

    public void deleteUser() throws SQLException {

        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();

        if (userList.isEmpty()) {
            System.out.print("Chua co nguoi dung, vui long them nguoi dung");
        } else {
            userList.forEach((user) -> {
                int i = 1;
                System.out.println(user.getId() + ". " + user.getTen() + " - " + user.getUserName());
            });
            System.out.print("Chon nguoi dung muon xoa : ");

            boolean deleted;
            int id;
            do {
                try {
                    id = Integer.parseInt(sc.nextLine());
                    if (adRepository.delete(id)) {
                        System.out.println("Xoa thanh cong!");
                        deleted = true;
                    } else {
                        System.out.println("KHong the xa nguoi dung, vui long thu lai!");
                        deleted = false;
                    }
                } catch (NumberFormatException | SQLException ex) {
                    deleted = false;
                    System.out.print("Vui long nhap so: ");
                }
            } while (!deleted);
        }
    }

    public void editUser() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();

        if (userList.isEmpty()) {
            System.out.println("Hien chua co nguoi dung.");
        } else {
            boolean choose = true;
            int id;
            int infoNum;
            do {
                try {
                    userList.forEach((anuser) -> {
                        int i = 1;
                        System.out.println(anuser.getId() + ". " + anuser.getTen() + " - " + anuser.getUserName());
                    });
                    System.out.println("Vui long nhap id nguoi dung cap nhat thong tin: ");
                    id = Integer.parseInt(sc.nextLine());
                    Admin user = adRepository.getbyId(id);
                    if (user == null) {
                        System.out.println("Nguoi dung khon ton tai");
                        choose = false;
                    } else {
                        int checkcontinue;
                        do {
                            System.out.println("------Thong tin nguoi dung------");
                            System.out.println("1. Ten: " + user.getTen());
                            System.out.println("2. Tai khoan: " + user.getUserName());
                            System.out.println("3. Mat khau: " + user.getPassword());
                            System.out.println("4. Quyen quan tri: " + user.getRole());
                            System.out.println("0. Quay lai");
                            System.out.println("Chon thong tin nguoi dung can cap nhat: ");
                            infoNum = Integer.parseInt(sc.nextLine());
                            switch (infoNum) {
                                case 1:
                                    System.out.println("Din ten nguoi dung");
                                    user.setTen(sc.nextLine());
                                    break;
                                case 2:
                                    System.out.println("Dien tai khoan nguoi dung");
                                    boolean validUsername;
                                    String username;
                                    do {
                                        username = sc.nextLine();
                                        validUsername = username.matches("[A-Za-z0-9]{2,18}");
                                        if (!validUsername) {
                                            System.out.print("ten tai khoan co d0 dai 2-18 ky tu!");
                                        } else {
                                            user.setUserName(username);
                                        }
                                    } while (!validUsername);
                                    break;
                                case 3:
                                    System.out.println("Nhap mat khau cho nguoi dung");
                                    boolean validPw;
                                    String pass;
                                    do {
                                        pass = sc.nextLine();
                                        validPw = pass.matches(".{3,20}");
                                        if (!validPw) {
                                            System.out.print("Mat khau co do dai 3-20 ky tu");
                                        } else {
                                            user.setPassword(pass);
                                        }
                                    } while (!validPw);
                                    break;
                                case 4:
                                    System.out.println("Chon quyen nguoi dung");
                                    System.out.println("1. Admin");
                                    System.out.println("2. Thu kho");
                                    int role;
                                    boolean check;
                                    do {
                                        check = true;

                                        role = Integer.parseInt(sc.nextLine());
                                        switch (role) {
                                            case 1:
                                                user.setRole("sa");
                                                break;
                                            case 2:
                                                user.setRole("thukho");
                                                break;
                                            default:
                                                check = false;
                                                System.out.println("Lua chon khong ton tai, vui long thu lai");
                                                break;
                                        }
                                    } while (!check);

                                    break;
                                default:
                                    System.out.println("Lua chon khong ton tai, vui long thu lai");
                                    break;

                            }
                            if (adRepository.update(user)) {
                                System.out.println("Cap nhat thanh con");
                            } else {
                                System.out.println("Vui long thu lai");
                            }
                            System.out.println("Bạn có muốn cập nhật thông tin khác không?");
                            System.out.println("1. Có");
                            System.out.println("0. Không");
                            checkcontinue = Integer.parseInt(sc.nextLine());
                        } while (checkcontinue != 0);

                    }
                } catch (NumberFormatException | SQLException ex) {
                    choose = false;
                    System.out.println("Lua chon khong ton tai, vui long thu lai");
                }
            } while (!choose);
            dataconn.Close();
        }
    }

    public void insertUser() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();
        Admin user = new Admin();
        System.out.println("---------Them nguoi dung---------");

        System.out.print("Username : ");
        boolean validUsername;
        String userName;
        do {
            userName = sc.nextLine();
            validUsername = userName.matches("[A-Za-z0-9]{3,30}");
            if (validUsername == false) {
                System.out.print("Ten tai khoan co do dai 3-10 ky tu : ");
            } else {
                if (adRepository.isExistUsername(userName) == false) {
                    user.setUserName(userName);
                } else {
                    validUsername = false;
                    System.out.println("Username da duoc su dung, vui long nhap username khac");
                }
            }
        } while (validUsername == false);

        System.out.print("Password : ");
        boolean validpass;
        String pass;
        do {
            pass = sc.nextLine();
            validpass = pass.matches(".{3,30}");
            if (validpass == false) {
                System.out.print("Mat khau co do dai 3-30 ky tu : ");
            } else {
                user.setPassword(pass);
            }
        } while (validpass == false);

        System.out.print("Ten : ");
        user.setTen(sc.nextLine());

        int role;
        boolean check;
        do {
            check = true;

            System.out.println("Quyen nguoi dung: ");
            System.out.println("1.Admin");
            System.out.println("2.Thu kho");
            role = Integer.parseInt(sc.nextLine());
            switch (role) {
                case 1:
                    user.setRole("sa");
                    break;
                case 2:
                    user.setRole("thukho");
                    break;
                default:
                    check = false;
                    System.out.println("Lua chon khong ton tai, vui long thu lai ");
                    break;
            }
        } while (!check);

        if (adRepository.insert(user) == true) {
            System.out.println("Them moi thanh cong");
        } else {
            System.out.println("Them moi khong thanh cong, vui long thu lai");
        }
    }

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<Admin> userList = adRepository.GetAll();

        String name = "";
        System.out.println("nhap username nguoi dung can tim kiem");
        name = sc.nextLine();
        Admin user = adRepository.findByUsername(name);
        user.displayInfo();
    }
}
