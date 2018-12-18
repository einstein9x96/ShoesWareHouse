package Repository;

import DataConnect.DataConnection;
import Model.DonHang;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonHangRepository {
    DataConnection dbconn;
    Connection conn;
    
    public DonHangRepository(DataConnection dconn ){
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }
    
    ArrayList<DonHang> listDH = new ArrayList<>();
    
    public ArrayList<DonHang> getAll() throws SQLException{
        String sql = "Select * from [DonHang]";
        
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            DonHang DH = new DonHang();
            DH.setId(rs.getInt("id"));
            DH.setMaThuKho(rs.getInt("ma_thukho"));
            DH.setNgayNhap(rs.getDate("ngay_nhap"));
            DH.setLoaiPhieu(rs.getInt("loai_phieu"));
            listDH.add(DH);
        }
        // Đóng Connect
       
        return listDH;
    }

    public boolean insert(DonHang DH) throws SQLException {
        String sql = "INSERT INTO [DonHang] VALUES(?,?,?,?)";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, DH.getId());
            stm.setInt(2, DH.getMaThuKho());
            stm.setDate(3, (Date) DH.getNgayNhap());
            stm.setInt(4, DH.getLoaiPhieu());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean update(DonHang DH) throws SQLException {
        String sql = "UPDATE [DonHang] SET id = ? , ma_thukho = ? , ngay_nhap = ? , loai_phieu = ? ";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, DH.getId());
            stm.setInt(2, DH.getMaThuKho());
            stm.setDate(3, (Date) DH.getNgayNhap());
            stm.setInt(4, DH.getLoaiPhieu());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [DonHang] where id = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public DonHang getbyId(int id) throws SQLException {
        
        String sql = "Select * from [DonHang] where id = " + id ;
        DonHang DH = new DonHang();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            DH.setId(rs.getInt("id"));
            DH.setMaThuKho(rs.getInt("ma_thukho"));
            DH.setNgayNhap(rs.getDate("ngay_nhap"));
            DH.setLoaiPhieu(rs.getInt("loai_phieu"));
        }
        return DH;
    }
}
