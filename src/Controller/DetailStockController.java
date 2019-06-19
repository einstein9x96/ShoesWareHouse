//
//package Controller;
//
//import DataConnect.DataConnection;
//import Model.ChiTietKho;
//
//
//import Repository.ChiTietKhoRepository;
//import Repository.HangHoaRepository;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class DetailStockController {
//
//    public Scanner scanner = new Scanner(System.in);
////    DataConnection dataconn = new DataConnection();
////    KhoRepository _khoRepository = new KhoRepository(dataconn);
////    ArrayList<Kho> list;
//
//    public void main(int id) throws SQLException {
//
//       
//    }
//
//   
//
//    public void displayName() {
//        System.out.printf("%-20s%-20s%-20s%-20s\n", "STT", "ID", "Ma hang", "So luong");
//    }
//
//    public String ChuanHoaChuoi(String NameInput) {
//        String Name = "";
//        NameInput = NameInput.toLowerCase();
//        String[] arr = NameInput.split(" ");
//        for (String s : arr) {
//            if (!s.isEmpty()) {
//                Name += String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1) + " ";
//            }
//        }
//
//        if (!Name.isEmpty()) {
//            Name = Name.substring(0, Name.length() - 1);
//        }
//        return Name;
//    }
//
//    public void displayList(ArrayList<ChiTietKho> list) throws SQLException {
//        DataConnection dataconn = new DataConnection();
//        HangHoaRepository _hhRepository = new HangHoaRepository(dataconn);
//        if (list.size() <= 0) {
//            System.out.print("Kho hien tai dang trong");
//        } else {
//            System.out.println("Thong tin kho : ");
//            int count = 1;
//            displayName();
//            for (ChiTietKho stock1 : list) {
//                String tenhang = _hhRepository.getbyId(stock1.getMaHang()).getTenHang();
//                System.out.printf("%-20.20s%-20.20s%-20.20s%-20.20s\n", count, stock1.getId(), tenhang, stock1.getSL());
//                count++;
//            }
//        }
//    }
//
//    public void printAll() throws SQLException {
//        DataConnection dataconn = new DataConnection();
//        ChiTietKhoRepository _chitiet = new ChiTietKhoRepository(dataconn);
//        ArrayList<ChiTietKho> stockList = _chitiet.getAll();
//        displayList(stockList);
//    }
//
//    
//
//    public void editStock() throws SQLException {
//        DataConnection dataconn = new DataConnection();
//        ChiTietKhoRepository _chitiet = new ChiTietKhoRepository(dataconn);
//        
//       
//
//        System.out.print("Chon thong tin muon sua theo id ");
//        
//    }
//}
//
//
// 
