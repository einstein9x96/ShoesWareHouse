package Controller;

import DataConnect.DataConnection;
import Model.NhomHang;
import Repository.NhomHangRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GroupGoodsController {

    public Scanner scanner = new Scanner(System.in);
//    DataConnection dataconn = new DataConnection();
//    NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
//    ArrayList<NhomHang> groupgoodslist;

    public void main() throws SQLException {

        Scanner in = new Scanner(System.in);
        boolean quit = false;
        int menuItem;
        do {
            menu();
            System.out.print("Nhap vao lua chon cua ban: ");
            try {
                menuItem = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                menuItem = 20;
            }
            switch (menuItem) {
                case 1:
                    GroupGoodslist();
                    scanner.nextLine();
                    break;
                case 2:
                    createGroupGoods();
                    scanner.nextLine();
                    break;
                case 3:
                    editGroupGoods();
                    scanner.nextLine();
                    break;
                case 4:
                    searchByName();
                    scanner.nextLine();
                    break;
                case 5:
                    deleteGroupGoods();
                    scanner.nextLine();
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.print("Lua chon khong dung, vui long chon lai: ");
                    break;
            }
        } while (!quit);

    }

    public void menu() {
        System.out.println("---------Quan ly nhom hang---------");
        System.out.println("1. Xem danh sach nhom hang");
        System.out.println("2. Them nhom hang");
        System.out.println("3. Sua thong tin nhom hang");
        System.out.println("4. Tim kiem nhom hang theo ten");
        System.out.println("5. Xoa nhom hang");
        System.out.println("0. Tro lai menu chinh");
    }

    public void displayName() {
        System.out.printf("%-20s%-20s\n", "Ma nhom", "Ten nhom");
    }
    
    public void displayList(ArrayList<NhomHang> list){
        if (list.size() <= 0) {
            System.out.print("Hien tai khong co nhom hang de hien thi");
        } else {
            System.out.println("Danh sach nhom hang : ");
            displayName();
            list.forEach((groupgoods1) -> {
                System.out.printf("%-20.20s%-20.20s\n", groupgoods1.getMaNhom(), groupgoods1.getTenNhom());
            });
        }
    }

    public String ChuanHoaChuoi(String NameInput) {
        String Name = "";
        NameInput = NameInput.toLowerCase();
        String[] arr = NameInput.split(" ");
        for (String s : arr) {
            if (!s.isEmpty()) {
                Name += String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1) + " ";
            }
        }

        if (!Name.isEmpty()) {
            Name = Name.substring(0, Name.length() - 1);
        }
        return Name;
    }

    public void GroupGoodslist() throws SQLException {
        DataConnection dataconn = new DataConnection();
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<NhomHang> groupgoodslist = _nhomhangRepository.getAll();

        displayList(groupgoodslist);

    }

    public void deleteGroupGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<NhomHang> groupgoodslist = _nhomhangRepository.getAll();
        displayList(groupgoodslist);
        
        System.out.print("Chon ma nhom hang muon xoa");

        int validSelectGroupGoods;
        int id = 0;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
                validSelectGroupGoods = 0;
            } catch (NumberFormatException ex) {
                validSelectGroupGoods = 1;
                System.out.print("Vui long nhap so : ");
            }
        } while (validSelectGroupGoods != 0);

        if (_nhomhangRepository.delete(id)) {
            System.out.print("Xoa nhom thanh cong");
        } else {
            System.out.print("Co loi xay ra, vui long thu lai");
        }
    }

    public void createGroupGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<NhomHang> groupgoodslist = _nhomhangRepository.getAll();
        
        System.out.print("Ten nhom hang : ");
        boolean validGroupGoodsName;
        String tennhomhang = "";
        do {
            tennhomhang = scanner.nextLine();
            validGroupGoodsName = tennhomhang.matches("^[A-Za-z\\s]{5,30}");
            if (validGroupGoodsName == false) {
                System.out.print("Ten nhom hang co do dai 5-30 ky tu ");
            } else {
                tennhomhang = ChuanHoaChuoi(tennhomhang);
            }
        } while (validGroupGoodsName == false);

        NhomHang groupgoods = new NhomHang();
        groupgoods.setTenNhom(tennhomhang);
        if (_nhomhangRepository.insert(groupgoods) == true) {
            System.out.println("Them nhom hang thanh cong");
        } else {
            System.out.println("Co loi xay ra, vui long kiem tra lai");
        }
    }

    public void editGroupGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<NhomHang> groupgoodslist = _nhomhangRepository.getAll();
        
        displayList(groupgoodslist);
        System.out.print("Chon nhom hang muon sua thong tin : ");

        int id = 0;
        boolean validchoosegroupgoods = true;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                validchoosegroupgoods = false;
                System.out.print("Vui long nhap so ");
            }
        } while (validchoosegroupgoods == false);

        NhomHang group = _nhomhangRepository.getbyId(id);
        if (group == null) {
            System.out.print("Khong tim thay nhom hang can sua");
        } else {
            System.out.print("Ten nhom hang : ");
            String tennhom;
            tennhom = scanner.nextLine();
            group.setTenNhom(tennhom);
            if (_nhomhangRepository.update(group)) {
                System.out.println("Cap nhat thanh cong!");
                dataconn.Close();
            } else {
                System.out.println("Co loi xay ra, vui long kiem tra lai");
            }
        }

    }

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<NhomHang> groupgoodslist = _nhomhangRepository.getAll();

        ArrayList<NhomHang> listSearch = new ArrayList<>();
        if (groupgoodslist.size() <= 0) {
            System.out.println("Hien tai khong co nhom hang nao");
        } else {
            System.out.print("Nhap ten nhom hang : ");
            String name = scanner.nextLine();
            for (NhomHang groupgoods : groupgoodslist) {
                if ((groupgoods.getTenNhom().toUpperCase()).contains(name.toUpperCase())) {
                    listSearch.add(groupgoods);
                }
            }
            if (listSearch.size() > 0) {
                displayList(listSearch);
            } else {
                System.out.println("Khong tim thay nhom hang phu hop.");
            }
        }

    }

}
