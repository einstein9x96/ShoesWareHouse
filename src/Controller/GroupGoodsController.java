/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import DataConnect.DataConnection;
import Model.NhomHang;
import Repository.NhomHangRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Chinh
 */
public class GroupGoodsController {
    
    public Scanner scanner = new Scanner(System.in);
    DataConnection dataconn = new DataConnection();
    NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
    ArrayList<NhomHang> groupgoodslist;
    

    public void main() throws SQLException {

        Scanner in = new Scanner(System.in);
        boolean quit = false;
        int menuItem = -1;
        do {
            System.out.println("---------Quản Lý Nhóm Hàng---------");
            menu();
            System.out.print("Nhập vào lựa chọn của bạn: ");
            try {
                menuItem = Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                menuItem = 20;
            }
            switch (menuItem) {
                case 1:
                    groupgoodslist = _nhomhangRepository.getAll();
                    GroupGoodslist(groupgoodslist);
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
                    System.out.print("Xin mời nhập lại ,hãy nhập từ 0 đến 5 : ");
                    break;
            }
        } while (!quit);

    }

    public void menu() {
        System.out.println("1. Xem danh sách kho hàng.");
        System.out.println("2. Thêm kho hàng.");
        System.out.println("3. Sửa thông tin kho hàng.");
        System.out.println("4. Tìm kiếm kho hàng theo tên ");
        System.out.println("5. Xóa kho hàng");
        System.out.println("0. Trở lại menu chính");
    }

    public void displayName() {
        System.out.printf("%-20s%-20s\n", "Mã nhóm", "Tên nhóm");
    }

    public String ChuanHoaChuoi(String NameInput) {
        String Name = "";
        NameInput = NameInput.toLowerCase();
        String[] arr = NameInput.split(" ");
        for (String s : arr) {
            if (!s.equals("") && !s.equals(null)) {
                Name += String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1) + " ";
            }
        }

        if (!Name.equals("") && !Name.equals(null)) {
            Name = Name.substring(0, Name.length() - 1);
        }
        return Name;
    }

    //Xem danh sách người dùng.
    public void GroupGoodslist(ArrayList<NhomHang> groupgoodsList) throws SQLException {

        if (groupgoodsList.size() <= 0) {
            System.out.print("Hiện tại không có nhóm hàng nào đề hiện thị");
        } else {
            System.out.println("Danh sách nhóm hàng : ");
           int count = 0;
            displayName();
            for (NhomHang groupgoods1 : groupgoodsList) {
                System.out.printf("%-20.20s%-20.20s\n", count, groupgoods1.getMaNhom(), groupgoods1.getTenNhom());
                count++;
            }
        }

    }

    // Xóa người dùng.
    public void deleteGroupGoods() throws SQLException {

        groupgoodslist = _nhomhangRepository.getAll();
        GroupGoodslist(groupgoodslist);
        System.out.print("Chọn nhóm hàng muốn xóa theo mã nhóm ");

        int validSelectGroupGoods = 0;
        int id = 0;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
                validSelectGroupGoods = 0;
            } catch (Exception ex) {
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

//Thêm người dùng.
    public void createGroupGoods() throws SQLException {

        //1: regex tên kho từ 3-30 kí tự        
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
//        //4:  mã kho > 5 ki tự
//        System.out.print("Mã kho hàng : ");
//        boolean validStockId;
//        String makho = "";
//        do {
//            makho = scanner.nextLine();
//             //"^[A-Za-z\\s]{5,30}$"
//             validStockId = makho.matches("[0-9]{3,10}");
//            if (validStockId == false) {
//                System.out.print("Mã kho gồm số từ 3 đến 10 kí tự ");
//            } 
//        } while (validStockId == false);

        //7: regex dia chi, tu 10-200 ki tu
        //System.out.print("Địa chỉ : ");
        //boolean validAddress;
        //String diadiem = "";
        //do {
        //    diadiem = scanner.nextLine();
        //    validAddress = diadiem.matches("^[A-Za-z0-9\\s\\,\\.\\-]{4,200}");
        //    if (validAddress == false) {
        //        System.out.print("Địa chỉ từ 10 đến 200 kí tự : ");
        //    }
        //} while (validAddress == false);

        NhomHang groupgoods = new NhomHang();
        groupgoods.setMaNhom(0);
        groupgoods.setTenNhom(tennhomhang);
        if (_nhomhangRepository.insert(groupgoods) == true) {
            System.out.println("Thêm thành công");
        } else {
            System.out.println("ID vừa nhập không tồn tại");
        }
    }
//
    //Sửa thông tin người dùng 

    public void editGroupGoods() throws SQLException {
        groupgoodslist = _nhomhangRepository.getAll();
        GroupGoodslist(groupgoodslist);

        System.out.print("Chọn nhóm hàng muốn sửa thông tin theo mã kho : ");

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

        NhomHang groupgoods = _nhomhangRepository.getbyId(id);
        if (groupgoods == null) {
            System.out.print("Không tìm thấy nhóm hàng muốn xóa ");
        } else {
            //1: regex Username từ 3-30 kí tự        
            System.out.print("Tên nhóm hàng : ");
            boolean validGroupGoodsName;
            String tennhom = "";
            do {
                tennhom = scanner.nextLine();
                validGroupGoodsName = tennhom.matches("^[A-Za-z\\s]{5,30}");
                if (validGroupGoodsName == false) {
                    System.out.print("Tên nhóm hàng gồm số và chữ thường 3 đến 30 kí tự ");
                } else {
                    tennhom = ChuanHoaChuoi(tennhom);
                }
            } while (validGroupGoodsName == false);
            //System.out.print("Địa chỉ : ");
            //boolean validAddress;
            //String diadiem = "";
            //do {
            //    diadiem = scanner.nextLine();
            //    validAddress = diadiem.matches("^[A-Za-z0-9\\s\\,\\.\\-]{4,200}");
            //    if (validAddress == false) {
            //        System.out.print("Địa chỉ từ 10 đến 200 kí tự : ");
            //    }
            //} while (validAddress == false);
            NhomHang groupgoods1 = new NhomHang();
            groupgoods.setTenNhom(tennhom);
            if (_nhomhangRepository.update(groupgoods1)) {
                System.out.println("Thay đổi thành công!");
            } else {
                System.out.println("Lỗi không xác định");
            }
        }

    }
    //Tìm kiếm thông tin người dùng 

    public void searchByName() throws SQLException {

        groupgoodslist = _nhomhangRepository.getAll();

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
                GroupGoodslist(listSearch);
            } else {
                System.out.println("Không tìm thấy nhóm hàng phù hợp.");
            }
        }

    }
    
}
