/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataConnect.DataConnection;
import Model.HangHoa;
import Model.NhomHang;
import Repository.HangHoaRepository;
import Repository.NhomHangRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Chinh
 */
public class CategoriesController {
    
    public Scanner scanner = new Scanner(System.in);
//    DataConnection dataconn = new DataConnection();
//    HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
//    NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
//    ArrayList<HangHoa> listhanghoa;

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
                    Goodslist();
                    scanner.nextLine();
                    break;
                case 2:
                    createGoods();
                    scanner.nextLine();
                    break;
                case 3:
                    editGoods();
                    scanner.nextLine();
                    break;
                case 4:
                    searchByName();
                    scanner.nextLine();
                    break;
                case 5:
                    deleteGoods();
                    scanner.nextLine();
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.println("Xin mời nhập lại ,hãy nhập từ 0 đến 5 : ");
                    break;
            }
        } while (!quit);
        
    }
    
    public void menu() {
        System.out.println("----------Quản lý hàng hóa----------");
        System.out.println("1. Xem danh sách hàng hóa");
        System.out.println("2. Thêm hàng hóa");
        System.out.println("3. Tìm kiếm hàng hóa theo tên");
        System.out.println("4. Sửa thông tin hàng hóa");
        System.out.println("5. Xóa hàng hóa");
        System.out.println("0. Trở lại menu chính");
    }
    
    public void displayName() {
        System.out.printf("%-20s%-20s%-20s%-20s\n", "STT", "Mã hàng hóa", "Tên hàng hóa", "Đơn vị tính");
    }
    
    public void displayList(ArrayList<HangHoa> list) {
        if (list.size() <= 0) {
            System.out.println("Hiện tại không có hàng hóa nào đề hiện thị");
        } else {
            System.out.println("Danh sách hàng hóa : ");
            int count = 0;
            displayName();
            for (HangHoa goods1 : list) {
                System.out.printf("%-20.20s%-20.20s%-20.20s%-20.20s\n", count, goods1.getMaHang(), goods1.getTenHang(), goods1.getDVT());
                count++;
            }
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

    //Xem danh sách người dùng.
    public void Goodslist() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();
        
        displayList(listhanghoa);
    }

    // Xóa người dùng.
    public void deleteGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();
        
        displayList(listhanghoa);
        System.out.print("Chọn hàng hóa muốn xóa theo mã ");
        
        int validSelectGoods;
        int id = 0;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
                validSelectGoods = 0;
            } catch (NumberFormatException ex) {
                validSelectGoods = 1;
                System.out.print("Vui lòng nhập số vào : ");
            }
        } while (validSelectGoods != 0);
        
        if (_HangHoaRepository.delete(id)) {
            System.out.println("Xóa hàng hóa thành công ");
        } else {
            System.out.println("Không thể xóa, vui lòng kiểm tra lại");
        }
    }

    public void createGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();
        
        
        System.out.print("Tên hàng hóa : ");
        boolean validGoodsName;
        String tenhanghoa = "";
        do {
            tenhanghoa = scanner.nextLine();
            validGoodsName = tenhanghoa.matches("^[A-Za-z\\s]{5,30}");
            if (validGoodsName == false) {
                System.out.print("Tên hàng hóa gồm số và chữ thường 3 đến 30 kí tự ");
            } else {
                tenhanghoa = ChuanHoaChuoi(tenhanghoa);
            }
        } while (validGoodsName == false);
        
        System.out.println("Danh sách nhóm hàng hóa có thể lựa chọn : ");
        ArrayList<NhomHang> nh = _nhomhangRepository.getAll();
        System.out.printf("%-20s%-20s\n", "Mã nhóm", "Tên nhóm");
        for (NhomHang nh1 : nh) {
            System.out.printf("%-20.20s%-20.20s\n", nh1.getMaNhom(), nh1.getTenNhom());
        }
        System.out.print("Mã nhóm : ");
        boolean validGoodsId;
        String manhom;
        int ma_nhom = 0;
        do {
            manhom = scanner.nextLine();
            //"^[A-Za-z\\s]{5,30}$"
            validGoodsId = true;
//                    manhom.matches("[0-9]{3,10}");
            if (validGoodsId == false) {
                System.out.print("Mã kho gồm số từ 3 đến 10 kí tự ");
            } else {
                ma_nhom = Integer.parseInt(manhom);
                NhomHang nhcheck = _nhomhangRepository.getbyId(ma_nhom);
                if (nhcheck == null) {
                    System.out.print("Vui lòng nhập mã nhóm trong danh sách");
                    validGoodsId = false;
                }
            }
        } while (validGoodsId == false);

        //7: regex dia chi, tu 10-200 ki tu
        System.out.print("Đơn vị tính : ");
        boolean validDVT;
        String dvt = "";
        do {
            dvt = scanner.nextLine();
            validDVT = dvt.matches("^[A-Za-z]{3,10}");
            if (validDVT == false) {
                System.out.print("Đơn vị tính từ 3 đến 10 kí tự : ");
            }
        } while (validDVT == false);
        
        HangHoa goods = new HangHoa(0, ma_nhom, tenhanghoa, dvt);
        if (_HangHoaRepository.insert(goods) == true) {
            System.out.println("Thêm thành công");
        } else {
            System.out.println("Có lỗi xảy ra,, vui lòng thử lại");
        }
    }
//
    //Sửa thông tin người dùng 

    public void editGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();
        displayList(listhanghoa);
        
        System.out.print("Chọn hàng hóa muốn sửa thông tin theo mã hàng hóa : ");
        
        int id = 0;
        boolean validchooseGoods = true;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                validchooseGoods = false;
                System.out.print("Vui lòng nhập số vào ");
            }
        } while (validchooseGoods == false);
        
        HangHoa goods = _HangHoaRepository.getbyId(id);
        if (goods == null) {
            System.out.print("Không tìm thấy hàng hóa muốn xóa ");
        } else {
            //1: regex Username từ 3-30 kí tự        
            System.out.print("Tên hàng hóa : ");
            boolean validGoodsName;
            String tenhanghoa = "";
            do {
                tenhanghoa = scanner.nextLine();
                validGoodsName = tenhanghoa.matches("^[A-Za-z\\s]{5,30}");
                if (validGoodsName == false) {
                    System.out.print("Tên hàng hóa hàng gồm số và chữ thường 3 đến 30 kí tự ");
                } else {
                    tenhanghoa = ChuanHoaChuoi(tenhanghoa);
                }
            } while (validGoodsName == false);
            System.out.print("Đơn vị tính : ");
            boolean validDVT;
            String dvt = "";
            do {
                dvt = scanner.nextLine();
                validDVT = dvt.matches("^[A-Za-z0-9\\s\\,\\.\\-]{3,10}");
                if (validDVT == false) {
                    System.out.print("Đơn vị tính từ 3 đến 10 kí tự : ");
                }
            } while (validDVT == false);
            HangHoa goods1 = new HangHoa(0, goods.getMaHang(), tenhanghoa, dvt);
            if (_HangHoaRepository.update(goods1)) {
                System.out.println("Thay đổi thành công!");
            } else {
                System.out.println("Có lỗi xảy ra, vui lòng thử lại");
            }
        }
        
    }
    //Tìm kiếm thông tin người dùng 

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();
        
        ArrayList<HangHoa> listSearch = new ArrayList<>();
        if (listhanghoa.size() <= 0) {
            System.out.println("Hiện tại không có hàng hóa nào");
        } else {
            System.out.print("Nhập tên hàng hóa: ");
            String name = scanner.nextLine();
            for (HangHoa goods : listhanghoa) {
                if ((goods.getTenHang().toUpperCase()).contains(name.toUpperCase())) {
                    listSearch.add(goods);
                }
            }
            if (listSearch.size() > 0) {
                displayList(listSearch);
            } else {
                System.out.println("Không tìm thấy hàng hóa phù hợp.");
            }
        }
        
    }
    
}
