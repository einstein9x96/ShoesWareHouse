package Controller;

import DataConnect.DataConnection;
import Model.ChiTietKho;
import Model.HangHoa;

import Repository.ChiTietKhoRepository;
import Repository.HangHoaRepository;
import Repository.KhoRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DetailStockController {

    public Scanner scanner = new Scanner(System.in);
//    DataConnection dataconn = new DataConnection();
//    KhoRepository _khoRepository = new KhoRepository(dataconn);
//    ArrayList<Kho> list;

    public void main(int id) throws SQLException {

        boolean quit = false;
        int menuItem;
        do {
            System.out.println("---------Quan ly chi tiet kho---------");
            menu();
            System.out.print("Nhap vao lua chon cua ban: ");
            try {
                menuItem = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                menuItem = 20;
            }
            switch (menuItem) {
                case 1:
                    DetailStockList(id);
                    scanner.nextLine();
                    break;
                case 2:
                    createStock(id);
                    scanner.nextLine();
                    break;
                case 3:
                    editStock(id);
                    scanner.nextLine();
                    break;
                case 4:
                    deleteStock(id);
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
        System.out.println("1. Xem chi tiet kho hang.");
        System.out.println("2. Them thong tin");
        System.out.println("3. Sua thong tin ");
        System.out.println("4. Xoa thong tin");
        System.out.println("0. Tro lai menu truoc");
    }

    public void displayName() {
        System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "STT", "ID", "Ma kho", "Ma hang", "So luong");
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

    public void displayList(ArrayList<ChiTietKho> list) throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        HangHoaRepository _hhRepository = new HangHoaRepository(dataconn);
        if (list.size() <= 0) {
            System.out.print("Kho hien tai dang trong");
        } else {
            System.out.println("Thong tin kho : ");
            int count = 1;
            displayName();
            for (ChiTietKho stock1 : list) {
                String tenkho = _khoRepository.getbyId(stock1.getMaKho()).getTenKho();
                String tenhang = _hhRepository.getbyId(stock1.getMaHang()).getTenHang();
                System.out.printf("%-20.20s%-20.20s%-20.20s%-20.20s%-20.20s\n", count, stock1.getId(), tenkho, tenhang, stock1.getSL());
                count++;
            }

        }
    }

    public void DetailStockList(int id) throws SQLException {
        DataConnection dataconn = new DataConnection();
        ChiTietKhoRepository _chitiet = new ChiTietKhoRepository(dataconn);
        ArrayList<ChiTietKho> stockList = _chitiet.getbyStockId(id);
        displayList(stockList);
    }

    public void deleteStock(int id) throws SQLException {
        DataConnection dataconn = new DataConnection();
        ChiTietKhoRepository _chitiet = new ChiTietKhoRepository(dataconn);
        ArrayList<ChiTietKho> stockList = _chitiet.getbyStockId(id);

        displayList(stockList);
        System.out.print("Chon kho muon xoa theo ma kho ");

        int validSelectStock;
        int ma = 0;
        do {
            try {
                ma = Integer.parseInt(scanner.nextLine());
                validSelectStock = 0;
            } catch (NumberFormatException ex) {
                validSelectStock = 1;
                System.out.print("Vui long nhap so vao : ");
            }
        } while (validSelectStock != 0);

        if (_chitiet.delete(id)) {
            System.out.print("Xoa thanh cong ");
        } else {
            System.out.print("Khong the xoa, vui long thu lai");
        }
    }

    public void createStock(int id) throws SQLException {
        DataConnection dataconn = new DataConnection();
        ChiTietKhoRepository _chitiet = new ChiTietKhoRepository(dataconn);
        HangHoaRepository _hanghoaRepository = new HangHoaRepository(dataconn);

        System.out.println("Danh sach hang hoa co the lua chon : ");
        ArrayList<HangHoa> hh = _hanghoaRepository.getAll();
        System.out.printf("%-20s%-20s\n", "Ma hang", "Ten hang");
        hh.forEach((hh1) -> {
            System.out.printf("%-20.20s%-20.20s\n", hh1.getMaHang(), hh1.getTenHang());
        });

        int validSelectGoods = 0;

        int mahang = 0;
        do {
            try {
                System.out.print("Ma hang : ");
                mahang = Integer.parseInt(scanner.nextLine());
                validSelectGoods = 0;
                HangHoa nhcheck = _hanghoaRepository.getbyId(mahang);
                if (nhcheck==null) {
                    System.out.print("Vui long chon ma hang co trong danh sach");
                    validSelectGoods = 1;
                }
            } catch (NumberFormatException ex) {
                validSelectGoods = 1;
                System.out.print("Vui long nhap dung ma hang ");
            }
        } while (validSelectGoods != 0);

        int soluong = 0;
        do {
            try {
                System.out.print("So luong :");
                soluong = Integer.parseInt(scanner.nextLine());
                validSelectGoods = 0;
                if(soluong<0){
                    System.out.println("So luong khong the nho hơn 0");
                    validSelectGoods=1;
                }
            } catch (NumberFormatException ex) {
                validSelectGoods = 1;
                System.out.print("Vui long nhap so ");
            }
        } while (validSelectGoods != 0);

        ChiTietKho stock = new ChiTietKho(0, id, mahang, soluong);
        if (_chitiet.insert(stock) == true) {
            System.out.println("Them thanh cong");
        } else {
            System.out.println("ID vua hap khong ton tai");
        }
    }

    public void editStock(int id) throws SQLException {
        DataConnection dataconn = new DataConnection();
        ChiTietKhoRepository _chitiet = new ChiTietKhoRepository(dataconn);
        
        DetailStockList(id);

        System.out.print("Chon thong tin muon sua theo id ");
        int validSelectGoods = 0;
        int id_sua = 0;
        do {
            try {
                id_sua = Integer.parseInt(scanner.nextLine());
                validSelectGoods = 0;
                ChiTietKho ct = _chitiet.checkExist(id_sua, id);
                if (ct == null) {
                    System.out.println("Vui long chon id co trong danh sach");
                    validSelectGoods = 1;
                }
            } catch (NumberFormatException ex) {
                validSelectGoods = 1;
                System.out.println("Vui long nhap so ");
            }
        } while (validSelectGoods != 0);
        ChiTietKho ct = _chitiet.checkExist(id_sua, id);
        System.out.printf("%-20s%-20s%-20s%-20s\n", "id", "Ma kho", "Ma hang", "So luong");
        System.out.printf("%-20s%-20s%-20s%-20s\n", ct.getId(), ct.getMaKho(), ct.getMaHang(), ct.getSL());
        System.out.println("Nhap so luong muon thay doi :");
        int soluong = 0;
        do {
            try {
                soluong = Integer.parseInt(scanner.nextLine());
                validSelectGoods = 0;
                if(soluong<0){
                    System.out.println("So luong khong the nho hơn 0");
                    validSelectGoods=1;
                }
            } catch (NumberFormatException ex) {
                validSelectGoods = 1;
                System.out.print("Vui long nhap so ");
            }
        } while (validSelectGoods != 0);
         ChiTietKho stock = new ChiTietKho(ct.getId(), ct.getMaKho(), ct.getMaHang(), soluong);
        if (_chitiet.update(stock)) {
            System.out.println("Cap nhat thanh cong!");
        } else {
            System.out.println("Co loi xay ra, vui long thu lai");
        }
    }

}


