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
            System.out.println("---------Quản Lý Kho---------");
            menu();
            System.out.print("Nhập vào lựa chọn của bạn: ");
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
        System.out.printf("%-20s%-20s%-20s%-20s\n", "STT", "Mã kho", "Tên kho", "Địa điểm");
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
            System.out.print("Hiện tại không có kho hàng nào đề hiện thị");
        } else {
            System.out.println("Danh sách kho hàng : ");
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
    }

    public void deleteStock() throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ArrayList<Kho> stockList = _khoRepository.getAll();

        displayList(stockList);
        System.out.print("Chọn kho muốn xóa theo mã kho ");

        int validSelectStock;
        int id = 0;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
                validSelectStock = 0;
            } catch (NumberFormatException ex) {
                validSelectStock = 1;
                System.out.print("Vui lòng nhập số vào : ");
            }
        } while (validSelectStock != 0);

        if (_khoRepository.delete(id)) {
            System.out.print("Xóa kho thành công ");
        } else {
            System.out.print("Lỗi không xác định");
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
                System.out.print("Tên kho hàng gồm số và chữ thường 3 đến 30 kí tự ");
            } else {
                tenkho = ChuanHoaChuoi(tenkho);
            }
        } while (validStockName == false);

        System.out.print("Địa chỉ : ");
        boolean validAddress;
        String diadiem = "";
        do {
            diadiem = scanner.nextLine();
            validAddress = diadiem.matches("^[A-Za-z0-9\\s\\,\\.\\-]{4,200}");
            if (validAddress == false) {
                System.out.print("Địa chỉ từ 10 đến 200 kí tự : ");
            }
        } while (validAddress == false);

        Kho stock = new Kho(0, tenkho, diadiem);
        if (_khoRepository.insert(stock) == true) {
            System.out.println("Thêm thành công");
        } else {
            System.out.println("ID vừa nhập không tồn tại");
        }
    }
//
    //Sửa thông tin người dùng 

    public void editStock() throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ArrayList<Kho> stockList = _khoRepository.getAll();
        displayList(stockList);

        System.out.print("Chọn kho muốn sửa thông tin theo mã kho : ");

        int id = 0;
        boolean validchooseStock = true;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                validchooseStock = false;
                System.out.print("Vui lòng nhập số vào ");
            }
        } while (validchooseStock == false);

        Kho stock = _khoRepository.getbyId(id);
        if (stock == null) {
            System.out.print("Không tìm thấy kho hàng muốn xóa ");
        } else {
            //1: regex Username từ 3-30 kí tự        
            System.out.print("Tên kho hàng : ");
            boolean validStockName;
            String tenkho = "";
            do {
                tenkho = scanner.nextLine();
                validStockName = tenkho.matches("^[A-Za-z\\s]{5,30}");
                if (validStockName == false) {
                    System.out.print("Tên kho hàng gồm số và chữ thường 3 đến 30 kí tự ");
                } else {
                    tenkho = ChuanHoaChuoi(tenkho);
                }
            } while (validStockName == false);
            System.out.print("Địa chỉ : ");
            boolean validAddress;
            String diadiem = "";
            do {
                diadiem = scanner.nextLine();
                validAddress = diadiem.matches("^[A-Za-z0-9\\s\\,\\.\\-]{4,200}");
                if (validAddress == false) {
                    System.out.print("Địa chỉ từ 10 đến 200 kí tự : ");
                }
            } while (validAddress == false);
            Kho stock1 = new Kho(stock.getMaKho(), tenkho, diadiem);
            if (_khoRepository.update(stock1)) {
                System.out.println("Thay đổi thành công!");
            } else {
                System.out.println("Lỗi không xác định");
            }
        }

    }
    //Tìm kiếm thông tin người dùng 

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ArrayList<Kho> stockList = _khoRepository.getAll();

        ArrayList<Kho> listSearch = new ArrayList<>();
        if (stockList.size() <= 0) {
            System.out.println("Hiện tại không có kho hàng nào");
        } else {
            System.out.print("Nhập tên kho hàng : ");
            String name = scanner.nextLine();
            for (Kho stock : stockList) {
                if ((stock.getTenKho().toUpperCase()).contains(name.toUpperCase())) {
                    listSearch.add(stock);
                }
            }
            if (listSearch.size() > 0) {
                displayList(listSearch);
            } else {
                System.out.println("Không tìm thấy kho hàng phù hợp.");
            }
        }

    }
}
