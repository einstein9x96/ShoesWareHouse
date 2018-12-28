package Controller;

import DataConnect.DataConnection;
import Model.Admin;
import Model.Kho;
import Repository.KhoRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StockController {

    public Scanner scanner = new Scanner(System.in);
//    DataConnection dataconn = new DataConnection();
//    KhoRepository _khoRepository = new KhoRepository(dataconn);
//    ArrayList<Kho> list;

    public void main(Admin user) throws SQLException {

        boolean quit = false;
        int menuItem;
        do {
            System.out.println("---------Quan ly kho---------");
            menu();
            System.out.print("Nhap vao lua chon cua ban: ");
            try {
                menuItem = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                menuItem = 20;
            }
            switch (menuItem) {
                case 1:
                    Stocklist();
                    scanner.nextLine();
                    break;
                case 2:
                    createStock();
                    scanner.nextLine();
                    break;
                case 3:
                    editStock();
                    scanner.nextLine();
                    break;
                case 4:
                    searchByName();
                    scanner.nextLine();
                    break;
                case 5:
                    deleteStock();
                    scanner.nextLine();
                    break;
                case 0:
                    quit = true;
//                    dataconn.Close();
                    break;
                default:
                    System.out.print("Lua chon khong ton tai, vui long nhap lai: ");
                    break;
            }
        } while (!quit);

    }

    public void menu() {
        System.out.println("1. Xem danh sach kho hang.");
        System.out.println("2. Them kho hang.");
        System.out.println("3. Sua thong tin kho hang.");
        System.out.println("4. Tim kiem kho hang theo ten ");
        System.out.println("5. Xoa kho hang");
        System.out.println("0. Tro lai menu chinh");
    }

    public void displayName() {
        System.out.printf("%-20s%-20s%-20s%-20s\n", "STT", "Ma kho", "Ten kho", "Dia diem");
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

    public void displayList(ArrayList<Kho> list) {
        if (list.size() <= 0) {
            System.out.print("Hien tai khong co kho hang nao de hien thi");
        } else {
            System.out.println("Danh sach kho hang: ");
            int count = 1;
            displayName();
            for (Kho stock1 : list) {
                System.out.printf("%-20.20s%-20.20s%-20.20s%-20.20s\n", count, stock1.getMaKho(), stock1.getTenKho(), stock1.getDiaDiem());
                count++;
            }

        }
    }

    public void Stocklist() throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ArrayList<Kho> stockList = _khoRepository.getAll();

        displayList(stockList);
        System.out.println("ban co muon xem chi tiet kho(y/n)");
        String check = scanner.nextLine();
        if (check.equalsIgnoreCase("y")) {
            System.out.println("Nhap ma kho ban muon xem chi tiet : ");
            int makhochitiet=0;
            boolean test = true;
            do {
                try {
                    makhochitiet = Integer.parseInt(scanner.nextLine());
                    test = false;
                } catch (NumberFormatException ex) {
                    System.out.println("Vui long nhap ma kho chinh xac");
                }
            } while (test == true);
            DetailStockController dst = new DetailStockController();
            dst.main(makhochitiet);
        }
    }

    public void deleteStock() throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ArrayList<Kho> stockList = _khoRepository.getAll();

        displayList(stockList);
        System.out.print("Chon kho muon xoa theo ma kho ");

        int validSelectStock;
        int id = 0;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
                validSelectStock = 0;
            } catch (NumberFormatException ex) {
                validSelectStock = 1;
                System.out.print("Vui long nhap so : ");
            }
        } while (validSelectStock != 0);

        if (_khoRepository.delete(id)) {
            System.out.print("Xoa kho thanh cong");
        } else {
            System.out.print("Co loi xay ra, vui long thu lai");
        }
    }

    public void createStock() throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ArrayList<Kho> stockList = _khoRepository.getAll();

        System.out.print("Tên kho hàng : ");
        boolean validStockName;
        String tenkho = "";
        do {
            tenkho = scanner.nextLine();
            validStockName = tenkho.matches("^[A-Za-z\\s]{5,30}");
            if (validStockName == false) {
                System.out.print("Ten kho co do dai 5-30 ky tu");
            } else {
                tenkho = ChuanHoaChuoi(tenkho);
            }
        } while (validStockName == false);

        System.out.print("Dia chi : ");
        boolean validAddress;
        String diadiem = "";
        do {
            diadiem = scanner.nextLine();
            validAddress = diadiem.matches("^[A-Za-z0-9\\s\\,\\.\\-]{4,200}");
            if (validAddress == false) {
                System.out.print("Dia chi co do dai 4-200 ky tu: ");
            }
        } while (validAddress == false);

        Kho stock = new Kho(0, tenkho, diadiem);
        if (_khoRepository.insert(stock) == true) {
            System.out.println("Them moi thanh cong");
        } else {
            System.out.println("Co loi xay ra, vui long thu lai");
        }
    }

    public void editStock() throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ArrayList<Kho> stockList = _khoRepository.getAll();
        displayList(stockList);

        System.out.print("Chon kho muon xoa thong tin theo ma kho: ");

        int id = 0;
        boolean validchooseStock = true;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                validchooseStock = false;
                System.out.print("Vui long nhap so: ");
            }
        } while (validchooseStock == false);

        Kho stock = _khoRepository.getbyId(id);
        if (stock == null) {
            System.out.print("Khong tim thay kho hang muon xoa ");
        } else {
            //1: regex Username từ 3-30 kí tự        
            System.out.print("Ten kho : ");
            boolean validStockName;
            String tenkho = "";
            do {
                tenkho = scanner.nextLine();
                validStockName = tenkho.matches("^[A-Za-z\\s]{5,30}");
                if (validStockName == false) {
                    System.out.print("Ten kho hang co do dai 5-30 ky tu ");
                } else {
                    tenkho = ChuanHoaChuoi(tenkho);
                }
            } while (validStockName == false);
            System.out.print("Dia chi : ");
            boolean validAddress;
            String diadiem = "";
            do {
                diadiem = scanner.nextLine();
                validAddress = diadiem.matches("^[A-Za-z0-9\\s\\,\\.\\-]{4,200}");
                if (validAddress == false) {
                    System.out.print("Dia chi co do dai 4-200 ky tu : ");
                }
            } while (validAddress == false);
            Kho stock1 = new Kho(stock.getMaKho(), tenkho, diadiem);
            if (_khoRepository.update(stock1)) {
                System.out.println("Cap nhat thanh cong!");
            } else {
                System.out.println("Co loi xay ra, vui long thu lai");
            }
        }

    }

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ArrayList<Kho> stockList = _khoRepository.getAll();

        ArrayList<Kho> listSearch = new ArrayList<>();
        if (stockList.size() <= 0) {
            System.out.println("Hien tai khong co kho hang nao");
        } else {
            System.out.print("Nhap ten kho hang : ");
            String name = scanner.nextLine();
            for (Kho stock : stockList) {
                if ((stock.getTenKho().toUpperCase()).contains(name.toUpperCase())) {
                    listSearch.add(stock);
                }
            }
            if (listSearch.size() > 0) {
                displayList(listSearch);
            } else {
                System.out.println("Khong tim thay kho hang phu hop.");
            }
        }

    }
}
