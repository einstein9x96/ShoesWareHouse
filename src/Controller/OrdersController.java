package Controller;

import DataConnect.DataConnection;
import Model.Admin;
import Model.ChiTietDonHang;
import Model.DonHang;
import Model.Kho;
import Repository.AdminRepository;
import Repository.ChiTietDonHangRepository;
import Repository.DonHangRepository;
import Repository.KhoRepository;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdersController {

    public Scanner scanner = new Scanner(System.in);

    public void main(Admin user) throws SQLException {

        boolean quit = false;
        int menuItem;
        do {
            menu();
            System.out.print("Nhap vao lua chon: ");
            try {
                menuItem = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                menuItem = 20;
            }
            switch (menuItem) {
                case 1:
                    getAllOrders();
                    scanner.nextLine();
                    break;
                case 2:
                    addNewOrder(user);
                    scanner.nextLine();
                    break;
                case 3:
                    viewDetailOrder();
                    scanner.nextLine();
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.println("Lua chon cua ban khong dung, vui long nhap lai");
                    break;
            }
        } while (!quit);
    }

    public void menu() {
        System.out.println("----------Quan ly don hang----------");
        System.out.println("1. Xem danh sách don hang");
        System.out.println("2. Them don hang");
        System.out.println("3. Xem chi tiet don hang");
        System.out.println("0. Tro lai menu chinh");
    }

    public void displayTitle() {
        System.out.printf("%-20s%-20s%-20s%-20s\n", "Ma don", "Nguoi quan ly", "Ngay nhap", "Loai phieu");
    }

    public void displayDetailTitle() {
        System.out.printf("%-20s%-20s%-20s%-20s\n", "Ma hang", "Loai hang", "So luong nhap", "Ma don");
    }

    public void displayList(ArrayList<DonHang> list) {
        DataConnection dataconn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataconn);
        list.forEach((order) -> {
            try {
                String loai;
                if (order.getLoaiPhieu() == 1) {
                    loai = "Don nhap hang";
                } else {
                    loai = "Don xuat hang";
                }
                Admin orderuser = adRepository.getbyId(order.getMaThuKho());
                System.out.printf("%-20.20s%-20.20s%-20.20s%-20.20s\n", order.getId(), orderuser.getTen(), order.getNgayNhap(), loai);
            } catch (SQLException ex) {
                Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void displayDetailList(ArrayList<ChiTietDonHang> list) {
        DataConnection dataconn = new DataConnection();
        KhoRepository hhRepository = new KhoRepository(dataconn);
        list.forEach((detail) -> {
            try {
                String loaihang = hhRepository.getbyId(detail.getMaHang()).getTenHang();
                System.out.printf("%-20.20s%-20.20s%-20.20s%-20.20s\n", detail.getMaHang(), loaihang, detail.getSLNhap(), detail.getMaDon());
            } catch (SQLException ex) {
                Logger.getLogger(OrdersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
    public void displayName() {
        System.out.printf("%-20s%-20s%-20s%-20s\n", "So luong", "Ten hang", "Ma hang", "Don vi tinh");
    }
    public void displayHang(ArrayList<Kho> list) {
        if (list.size() <= 0) {
            System.out.println("Hien tai khong co hang hoa nao de hien thi");
        } else {
            System.out.println("Danh sach hang hoa : ");
            int count = 1;
            displayName();
            for (Kho goods1 : list) {
                System.out.printf("%-20.20s%-20.20s%-20.20s%-20.20s\n", count, goods1.getTenHang(), goods1.getMaHang(), goods1.getDVT());
                count++;
            }
        }
    }

    public void getAllOrders() throws SQLException {
        DataConnection dataconn = new DataConnection();
        DonHangRepository orRepository = new DonHangRepository(dataconn);
        AdminRepository adRepository = new AdminRepository(dataconn);
        ArrayList<DonHang> orList = orRepository.getAll();

        if (orList.size() <= 0) {
            System.out.print("Hien tai chua co don hang trong co so du lieu");
        } else {
            System.out.println("Danh sach don hang : ");
            displayTitle();
            displayList(orList);
        }
    }

    public void addNewOrder(Admin user) throws SQLException {
        DataConnection dataconn = new DataConnection();
        DonHangRepository orRepository = new DonHangRepository(dataconn);
        KhoRepository _khoRepository = new KhoRepository(dataconn);
        ChiTietDonHangRepository _orderDetail = new ChiTietDonHangRepository(dataconn);
        
        DonHang order = new DonHang();
        order.setMaThuKho(user.getId());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        order.setNgayNhap(dtf.format(localDate));

        System.out.println("Chon loai phieu");
        System.out.println("1. Phieu nhap");
        System.out.println("0. Phieu xuat");
        int kind = 20;
        boolean validInputNum = true;
        do {
            try {
                kind = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                validInputNum = false;
                System.out.print("Vui long nhap so vao ");
            }
        } while (validInputNum == false);
        boolean checkinput;
        do {
            checkinput = true;
            switch (kind) {
                case 1:
                    order.setLoaiPhieu(kind);
                    break;
                case 0:
                    order.setLoaiPhieu(kind);
                    break;
                default:
                    checkinput = false;
                    System.out.println("Lua chon cua ban khong dung, vui long chon lai");
                    break;
            }
        } while (!checkinput);
        // lấy id sau khi insert
        int id = orRepository.insert(order);
        if (id > 0) {
            System.out.println("Them chi tiet don hang");
            //tạo 1 list chi tiết đơn hàng
            ArrayList<ChiTietDonHang> lstCTDH = new ArrayList<>();
            boolean isContinue; // kiểm tra xem có nhập tiếp hay k
            do {
                isContinue = true;
                ChiTietDonHang item = new ChiTietDonHang();
                
                ArrayList<Kho> listHang = _khoRepository.getAll();
                System.out.println("Danh sach ma hang");
                displayHang(listHang);
                
                System.out.println("Vui long nhap ma hang: ");
                boolean validInputNum3 = true;
                int mahang = 0;
                do {
                    try {
                        mahang = Integer.parseInt(scanner.nextLine());
                        Kho existHang = _khoRepository.getbyId(mahang);
                        if(existHang == null){
                            System.out.println("Ma hang khong ton tai");
                            validInputNum3 = false;
                        }
                    
                    } catch (NumberFormatException ex) {
                        validInputNum3 = false;
                        System.out.print("Vui long nhap so vao ");
                    }
                } while (validInputNum3 == false);
                        
                System.out.println("Nhap so luong hang: ");
                boolean validInputNum2 = true;
                int soluong = 0;
                do {
                    try {
                        soluong = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException ex) {
                        validInputNum2 = false;
                        System.out.print("Vui long nhap so vao ");
                    }
                } while (validInputNum2 == false);
                
                item.setSLNhap(soluong);
                item.setMaHang(mahang);
                item.setMaDon(id);
                
                lstCTDH.add(item);
                
                System.out.println("Bạn có muốn nhập tiếp?(1.yes/2.no)");
                int choice = 0;
                boolean validInputNum4 = true;
                do {
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                        if(choice == 1){
                            isContinue = true;
                        }
                        else if(choice == 2){
                            isContinue = false;
                        }
                        else{
                            System.out.print("Lua chon cua ban khong dung, vui long chon lai ");
                        }
                    } catch (NumberFormatException ex) {
                        validInputNum4 = false;
                        System.out.print("Vui long nhap so vao ");
                    }
                } while (validInputNum4 == false);
            } while (!isContinue);
            
            for(ChiTietDonHang item : lstCTDH){
                if(order.getLoaiPhieu() == 1){
                    Kho hanghoa = _khoRepository.getbyId(item.getId());
                    hanghoa.setSoLuong(hanghoa.getSoLuong() + item.getSLNhap());
                    _khoRepository.update(hanghoa);
                    _orderDetail.insert(item);
                }
                else{
                    Kho hanghoa = _khoRepository.getbyId(item.getId());
                    hanghoa.setSoLuong(hanghoa.getSoLuong() - item.getSLNhap());
                    _khoRepository.update(hanghoa);
                    _orderDetail.insert(item);
                }
            }
            
        } else {
            System.out.println("Co loi xay ra, vui long thu lai");
        }
    }

//    public void updateOrder() throws SQLException {
//        DataConnection dataconn = new DataConnection();
//        DonHangRepository orRepository = new DonHangRepository(dataconn);
//
//        ArrayList<DonHang> orderList = orRepository.getAll();
//        displayTitle();
//        displayList(orderList);
//        int id = 0;
//        boolean check;
//        do {
//            check = true;
//            System.out.println("Vui long chon don hang can cap nhat thong tin: ");
//
//            boolean validInputNum = true;
//            do {
//                try {
//                    id = Integer.parseInt(scanner.nextLine());
//                } catch (NumberFormatException ex) {
//                    validInputNum = false;
//                    System.out.print("Vui long nhap so vao ");
//                }
//            } while (validInputNum == false);
//            DonHang existOrder = orRepository.getbyId(id);
//
//            if (existOrder == null) {
//                System.out.println("Don hang khong ton tai, vui long kiem tra lai");
//            } else {
//                Date date2 = new Date();
//                boolean checkDate = true;
//                String date;
//                do {
//                    System.out.println("Cap nhat ngay lap don hang (dd/mm/yyyy):");
//                    date = scanner.nextLine();
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//                    try {
//                        date2 = dateFormat.parse(date);
//                    } catch (ParseException e) {
//                        checkDate = false;
//                        System.out.println("Dinh dang ngay cua ban khong dung, vui long nhap lai");
//                    }
//                } while (!checkDate);
//                existOrder.setNgayNhap(date);
//            }
//
//            if (orRepository.update(existOrder)) {
//                System.out.println("Cap nhat thanh cong");
//            } else {
//                check = false;
//                System.out.println("Co loi xay ra, vui long thu lai");
//            }
//        } while (!check);
//
//    }


    public void viewDetailOrder() throws SQLException {
        DataConnection dataconn = new DataConnection();
        DonHangRepository orRepository = new DonHangRepository(dataconn);
        ChiTietDonHangRepository _orderDetail = new ChiTietDonHangRepository(dataconn);
        getAllOrders();
        int madon = 0;
        System.out.println("Chon 1 don hang de xem chi tiet");
//        madon = Integer.parseInt(scanner.nextLine());
        boolean validInputNum = true;
        do {
            try {
                madon = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                validInputNum = false;
                System.out.print("Vui long nhap so vao ");
            }
        } while (validInputNum == false);
        ArrayList<ChiTietDonHang> orderDetailList = _orderDetail.getbyMaDonHang(madon);
        if (orderDetailList == null) {
            System.out.println("Khong co chi tiet don hang cho don hang nay");
        } else {
            displayDetailTitle();
            displayDetailList(orderDetailList);
        }
    }

//    public void updateDetailOrder() throws SQLException {
//        DataConnection dataconn = new DataConnection();
//        DonHangRepository orRepository = new DonHangRepository(dataconn);
//        ChiTietDonHangRepository _orderDetail = new ChiTietDonHangRepository(dataconn);
//        int id = 0;
//        viewDetailOrder();
//        System.out.println("Chon chi tiet don hang ban muon cap nhat");
////        id = Integer.parseInt(scanner.nextLine());
//        boolean validInputNum = true;
//        do {
//            try {
//                id = Integer.parseInt(scanner.nextLine());
//            } catch (NumberFormatException ex) {
//                validInputNum = false;
//                System.out.print("Vui long nhap so vao ");
//            }
//        } while (validInputNum == false);
//        ChiTietDonHang orderDetail = _orderDetail.getbyId(id);
//
//        if (orderDetail == null) {
//            System.out.println("Chi tiet don hang khong ton tai, vui long kiem tra lai");
//        } else {
//            boolean checkUpdate;
//            do {
//                checkUpdate = true;
//                int mahang = 0;
//                System.out.println("Chon ma hang cho chi tiet don hang");
//                GroupGoodsController groupGoodsController = new GroupGoodsController();
//                groupGoodsController.GroupGoodslist();
////                mahang = Integer.parseInt(scanner.nextLine());
//
//                boolean validInputNum2 = true;
//                do {
//                    try {
//                        mahang = Integer.parseInt(scanner.nextLine());
//                    } catch (NumberFormatException ex) {
//                        validInputNum2 = false;
//                        System.out.print("Vui long nhap so vao ");
//                    }
//                } while (validInputNum2 == false);
//                orderDetail.setMaHang(mahang);
//                System.out.println("Dien so luong nhap/xuat");
//                int slnhap = 0;
//                boolean validInputNum3 = true;
//                do {
//                    try {
//                        slnhap = Integer.parseInt(scanner.nextLine());
//                    } catch (NumberFormatException ex) {
//                        validInputNum3 = false;
//                        System.out.print("Vui long nhap so vao ");
//                    }
//                } while (validInputNum3 == false);
//                orderDetail.setSLNhap(slnhap);
//                if (_orderDetail.update(orderDetail)) {
//                    System.out.println("Cap nhat thanh cong");
//                } else {
//                    checkUpdate = false;
//                    System.out.println("Co loi xay ra, vui long thu lai");
//                }
//            } while (!checkUpdate);
//
//        }
//    }
//
//    public void addDetailOrder() throws SQLException {
//        DataConnection dataconn = new DataConnection();
//        DonHangRepository orRepository = new DonHangRepository(dataconn);
//        ChiTietDonHangRepository _orderDetail = new ChiTietDonHangRepository(dataconn);
//        boolean insert;
//        do {
//            insert = true;
//
//            ChiTietDonHang newDetail = new ChiTietDonHang();
//            int mahang = 0;
//            int madon = 0;
//            boolean check;
//            getAllOrders();
//            do {
//                check = true;
//                System.out.println("Chon 1 don hang de them chi tiet don hang");
////                madon = Integer.parseInt(scanner.nextLine());
//                boolean validInputNum = true;
//                do {
//                    try {
//                        madon = Integer.parseInt(scanner.nextLine());
//                    } catch (NumberFormatException ex) {
//                        validInputNum = false;
//                        System.out.print("Vui long nhap so vao ");
//                    }
//                } while (validInputNum == false);
//                DonHang order = orRepository.getbyId(madon);
//                if (order == null) {
//                    check = false;
//                    System.out.println("don hang khong ton tai, moi ban chon lai");
//                } else {
//                    newDetail.setMaDon(madon);
//                }
//            } while (!check);
//
//            System.out.println("Chon ma hang cho chi tiet don hang");
//            GroupGoodsController groupGoodsController = new GroupGoodsController();
//            groupGoodsController.GroupGoodslist();
////            mahang = Integer.parseInt(scanner.nextLine());
//            boolean validInputNum = true;
//            do {
//                try {
//                    mahang = Integer.parseInt(scanner.nextLine());
//                } catch (NumberFormatException ex) {
//                    validInputNum = false;
//                    System.out.print("Vui long nhap so vao ");
//                }
//            } while (validInputNum == false);
//            newDetail.setMaHang(mahang);
//
//            System.out.println("Dien so luong nhap/xuat");
//            int slnhap = 0;
//            boolean validInputNum3 = true;
//            do {
//                try {
//                    slnhap = Integer.parseInt(scanner.nextLine());
//                } catch (NumberFormatException ex) {
//                    validInputNum3 = false;
//                    System.out.print("Vui long nhap so vao ");
//                }
//            } while (validInputNum3 == false);
//            newDetail.setSLNhap(slnhap);
//
//            if (_orderDetail.insert(newDetail)) {
//                System.out.println("Them moi thanh cong");
//            } else {
//                insert = false;
//                System.out.println("Co loi xay ra, vui long thu lai");
//            }
//        } while (!insert);
//
//    }
}
