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
            System.out.print("Nhập vào lựa chọn của bạn: ");
            try {
                menuItem = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                menuItem = 20;
            }
            switch (menuItem) {
                case 1:
                    GroupGoodslist();
                    System.out.println("Ấn Enter để quay lại");
                    scanner.nextLine();
                    break;
                case 2:
                    createGroupGoods();
                    System.out.println("Ấn Enter để quay lại");
                    scanner.nextLine();
                    break;
                case 3:
                    editGroupGoods();
                    System.out.println("Ấn Enter để quay lại");
                    scanner.nextLine();
                    break;
                case 4:
                    searchByName();
                    System.out.println("Ấn Enter để quay lại");
                    scanner.nextLine();
                    break;
                case 5:
                    deleteGroupGoods();
                    System.out.println("Ấn Enter để quay lại");
                    scanner.nextLine();
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.print("Xin mời nhập lại ,hãy nhập từ 0 đến 5 : ");
                    break;
            }
        } while (!quit);

    }

    public void menu() {
        System.out.println("---------Quản Lý Nhóm Hàng---------");
        System.out.println("1. Xem danh sách nhóm hàng");
        System.out.println("2. Thêm nhóm hàng");
        System.out.println("3. Sửa thông tin nhóm hàng");
        System.out.println("4. Tìm kiếm nhóm hàng theo tên");
        System.out.println("5. Xóa nhóm hàng");
        System.out.println("0. Trở lại menu chính");
    }

    public void displayName() {
        System.out.printf("%-20s%-20s\n", "Mã nhóm", "Tên nhóm");
    }
    
    public void displayList(ArrayList<NhomHang> list){
        if (list.size() <= 0) {
            System.out.print("Hiện tại không có nhóm hàng nào đề hiện thị");
        } else {
            System.out.println("Danh sách nhóm hàng : ");
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
        
        System.out.print("Chọn nhóm hàng muốn xóa theo mã nhóm ");

        int validSelectGroupGoods;
        int id = 0;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
                validSelectGroupGoods = 0;
            } catch (NumberFormatException ex) {
                validSelectGroupGoods = 1;
                System.out.print("Vui lòng nhập số vào : ");
            }
        } while (validSelectGroupGoods != 0);

        if (_nhomhangRepository.delete(id)) {
            System.out.print("Xóa nhóm hàng thành công ");
        } else {
            System.out.print("Lỗi không xác định");
        }
    }

    public void createGroupGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<NhomHang> groupgoodslist = _nhomhangRepository.getAll();
        
        System.out.print("Tên nhóm hàng : ");
        boolean validGroupGoodsName;
        String tennhomhang = "";
        do {
            tennhomhang = scanner.nextLine();
            validGroupGoodsName = tennhomhang.matches("^[A-Za-z\\s]{5,30}");
            if (validGroupGoodsName == false) {
                System.out.print("Tên nhóm hàng gồm số và chữ thường 3 đến 30 kí tự ");
            } else {
                tennhomhang = ChuanHoaChuoi(tennhomhang);
            }
        } while (validGroupGoodsName == false);

        NhomHang groupgoods = new NhomHang();
        groupgoods.setTenNhom(tennhomhang);
        if (_nhomhangRepository.insert(groupgoods) == true) {
            System.out.println("Thêm nhóm hàng thành công");
        } else {
            System.out.println("Thêm mới nhóm hàng không thành công, vui lòng kiểm tra lại");
        }
    }

    public void editGroupGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<NhomHang> groupgoodslist = _nhomhangRepository.getAll();
        
        displayList(groupgoodslist);
        System.out.print("Chọn nhóm hàng muốn sửa thông tin : ");

        int id = 0;
        boolean validchoosegroupgoods = true;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                validchoosegroupgoods = false;
                System.out.print("Vui lòng nhập số vào ");
            }
        } while (validchoosegroupgoods == false);

        NhomHang group = _nhomhangRepository.getbyId(id);
        if (group == null) {
            System.out.print("Không tìm thấy nhóm hàng cần sửa");
        } else {
            System.out.print("Tên nhóm hàng : ");
            String tennhom;
            tennhom = scanner.nextLine();
            group.setTenNhom(tennhom);
            if (_nhomhangRepository.update(group)) {
                System.out.println("Thay đổi thành công!");
                dataconn.Close();
            } else {
                System.out.println("Có lỗi xảy ra, vui lòng kiểm tra lại");
            }
        }

    }

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<NhomHang> groupgoodslist = _nhomhangRepository.getAll();

        ArrayList<NhomHang> listSearch = new ArrayList<>();
        if (groupgoodslist.size() <= 0) {
            System.out.println("Hiện tại không có nhóm hàng nào");
        } else {
            System.out.print("Nhập tên nhóm hàng : ");
            String name = scanner.nextLine();
            for (NhomHang groupgoods : groupgoodslist) {
                if ((groupgoods.getTenNhom().toUpperCase()).contains(name.toUpperCase())) {
                    listSearch.add(groupgoods);
                }
            }
            if (listSearch.size() > 0) {
                displayList(listSearch);
            } else {
                System.out.println("Không tìm thấy nhóm hàng phù hợp.");
            }
        }

    }

}
