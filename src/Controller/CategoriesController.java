package Controller;

import DataConnect.DataConnection;
import Model.HangHoa;
import Model.NhomHang;
import Repository.HangHoaRepository;
import Repository.NhomHangRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CategoriesController {

    public Scanner scanner = new Scanner(System.in);
//    DataConnection dataconn = new DataConnection();
//    HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
//    NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
//    ArrayList<HangHoa> listhanghoa;

    public void main() throws SQLException {

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
                    System.out.println("Lua chon cua ban khong dung, vui long nhap lai: ");
                    break;
            }
        } while (!quit);

    }

    public void menu() {
        System.out.println("----------Quan ly hang hoa----------");
        System.out.println("1. Xem danh sach hang hoa");
        System.out.println("2. Them hang hoa");
        System.out.println("3. Sua thong tin hang hoa");
        System.out.println("4. Tim kiem hang hoa");
        System.out.println("5. Xoa hang hoa");
        System.out.println("0. Tro lai menu chinh");
    }

    public void displayName() {
        System.out.printf("%-20s%-20s%-20s%-20s\n", "STT", "Ma hang", "Ten hang", "Don vi tinh");
    }

    public void displayList(ArrayList<HangHoa> list) {
        if (list.size() <= 0) {
            System.out.println("Hien tai khong co hang hoa nao de hien thi");
        } else {
            System.out.println("Danh sach hang hoa : ");
            int count = 1;
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

    public void Goodslist() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();

        displayList(listhanghoa);
    }

    public void deleteGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();

        displayList(listhanghoa);
        System.out.print("Chon hang hoa muon xoa theo ma");

        int validSelectGoods;
        int id = 0;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
                validSelectGoods = 0;
            } catch (NumberFormatException ex) {
                validSelectGoods = 1;
                System.out.print("Vui long nhap so vao : ");
            }
        } while (validSelectGoods != 0);

        if (_HangHoaRepository.delete(id)) {
            System.out.println("Xoa hang hoa thanh cong");
        } else {
            System.out.println("Khong the xoa, vui long kiem tra lai");
        }
    }

    public void createGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();

        System.out.print("Ten hang hoa : ");
        boolean validGoodsName;
        String tenhanghoa = "";
        do {
            tenhanghoa = scanner.nextLine();
            validGoodsName = tenhanghoa.matches("^[A-Za-z\\s]{5,50}");
            if (validGoodsName == false) {
                System.out.print("Ten hang hoa gom ky tu va so cos do dai 5-50 ky tu");
            } else {
                tenhanghoa = ChuanHoaChuoi(tenhanghoa);
            }
        } while (validGoodsName == false);

        System.out.println("Danh sach nhom hang hoa co the lua chon : ");
        ArrayList<NhomHang> nh = _nhomhangRepository.getAll();
        System.out.printf("%-20s%-20s\n", "Ma nhom", "Ten nhom");
        for (NhomHang nh1 : nh) {
            System.out.printf("%-20.20s%-20.20s\n", nh1.getMaNhom(), nh1.getTenNhom());
        }
        System.out.print("Ma nhom : ");
        boolean validGoodsId;
        int ma_nhom = 0;
        do {
            validGoodsId = true;
            boolean test = true;
            do {
                try {
                    ma_nhom = Integer.parseInt(scanner.nextLine());
                    test = false;
                } catch (NumberFormatException ex) {
                    System.out.println("Vui long nhap ma chinh xac");
                }
            } while (test == true);
            NhomHang nhcheck = _nhomhangRepository.getbyId(ma_nhom);
            if (nhcheck == null) {
                System.out.print("Vui long nhap ma nhom trong danh sach");
                validGoodsId = false;
            }
        } while (validGoodsId == false);

        System.out.print("Don vi tinh : ");
        boolean validDVT;
        String dvt = "";
        do {
            dvt = scanner.nextLine();
            validDVT = dvt.matches("^[A-Za-z]{3,10}");
            if (validDVT == false) {
                System.out.print("Don vi tinh co do dai 3-10 ky tu: ");
            }
        } while (validDVT == false);

        HangHoa goods = new HangHoa(0, ma_nhom, tenhanghoa, dvt);
        if (_HangHoaRepository.insert(goods) == true) {
            System.out.println("Them thanh cong");
        } else {
            System.out.println("Co loi xay ra, vui long thu lai");
        }
    }

    public void editGoods() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();
        displayList(listhanghoa);

        System.out.print("Chon hang hoa cap nhat thong tin theo ma hang hoa: ");

        int id = 0;
        boolean validchooseGoods = true;
        do {
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                validchooseGoods = false;
                System.out.print("Vui long nhap so vao ");
            }
        } while (validchooseGoods == false);

        HangHoa goods = _HangHoaRepository.getbyId(id);
        if (goods == null) {
            System.out.print("Khong tim thay hang hoa muon xoa ");
        } else {
            System.out.print("Ten hang hoa : ");
            boolean validGoodsName;
            String tenhanghoa = "";
            do {
                tenhanghoa = scanner.nextLine();
                validGoodsName = tenhanghoa.matches("^[A-Za-z\\s]{5,50}");
                if (validGoodsName == false) {
                    System.out.print("Ten hang hoa co do dai tu 5-50 ky tu ");
                } else {
                    tenhanghoa = ChuanHoaChuoi(tenhanghoa);
                }
            } while (validGoodsName == false);
            System.out.print("Don vi tinh : ");
            boolean validDVT;
            String dvt = "";
            do {
                dvt = scanner.nextLine();
                validDVT = dvt.matches("^[A-Za-z0-9\\s\\,\\.\\-]{3,10}");
                if (validDVT == false) {
                    System.out.print("Don vi tinh co do dai 3-10 ky tu:  ");
                }
            } while (validDVT == false);
            HangHoa goods1 = new HangHoa(goods.getMaHang(), goods.getMaNhom(), tenhanghoa, dvt);
            if (_HangHoaRepository.update(goods1)) {
                System.out.println("Cap nhat thanh cong!");
            } else {
                System.out.println("Co loi xay ra vui long thu lai");
            }
        }

    }

    public void searchByName() throws SQLException {
        DataConnection dataconn = new DataConnection();
        HangHoaRepository _HangHoaRepository = new HangHoaRepository(dataconn);
        NhomHangRepository _nhomhangRepository = new NhomHangRepository(dataconn);
        ArrayList<HangHoa> listhanghoa = _HangHoaRepository.getAll();

        ArrayList<HangHoa> listSearch = new ArrayList<>();
        if (listhanghoa.size() <= 0) {
            System.out.println("Hien tai khong co hang hoa nao");
        } else {
            System.out.print("Nhap ten hang hoa: ");
            String name = scanner.nextLine();
            for (HangHoa goods : listhanghoa) {
                if ((goods.getTenHang().toUpperCase()).contains(name.toUpperCase())) {
                    listSearch.add(goods);
                }
            }
            if (listSearch.size() > 0) {
                displayList(listSearch);
            } else {
                System.out.println("Khong tim thay hang hoa phu hop.");
            }
        }

    }

}
