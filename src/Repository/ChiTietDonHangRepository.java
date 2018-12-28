package Repository;

import DataConnect.DataConnection;
import Model.ChiTietDonHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChiTietDonHangRepository {

    DataConnection dbconn;
    Connection conn;

    public ChiTietDonHangRepository(DataConnection dconn) {
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }

    public ArrayList<ChiTietDonHang> getAll() throws SQLException {

        ArrayList<ChiTietDonHang> listCTDH = new ArrayList<>();

        String sql = "Select * from [ChiTietDonHang]";

        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            ChiTietDonHang CTDH = new ChiTietDonHang();
            CTDH.setId(rs.getInt("id"));
            CTDH.setMaHang(rs.getInt("ma_hang"));
            CTDH.setSLNhap(rs.getInt("soluong_nhap"));
            CTDH.setMaDon(rs.getInt("ma_don"));
            listCTDH.add(CTDH);
        }
        // Đóng Connect

        return listCTDH;
    }

    public boolean insert(ChiTietDonHang CTDH) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "INSERT INTO [ChiTietDonHang] VALUES(" + CTDH.getMaHang() + "," + CTDH.getSLNhap() + "," + CTDH.getMaDon() + ")";
        boolean is;
        is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean update(ChiTietDonHang CTDH) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "UPDATE [ChiTietDonHang] SET ma_hang = " + CTDH.getMaHang() + " , soluong_nhap = " + CTDH.getSLNhap() + " ,"
                + " ma_don = " + CTDH.getMaDon() + " where id = " + CTDH.getId();
        boolean is;
        is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [ChiTietDonHang] where id = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public ChiTietDonHang getbyId(int id) throws SQLException {

        String sql = "Select * from [ChiTietDonHang] where id = " + id;
        ChiTietDonHang ctdh = new ChiTietDonHang();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            ctdh.setId(rs.getInt("id"));
            ctdh.setMaHang(rs.getInt("ma_hang"));
            ctdh.setSLNhap(rs.getInt("soluong_nhap"));
            ctdh.setMaDon(rs.getInt("ma_don"));
        }
        return ctdh;
    }

    public ArrayList<ChiTietDonHang> getbyMaDonHang(int madon) throws SQLException {

        String sql = "Select * from [ChiTietDonHang] where ma_don = " + madon;
        ChiTietDonHang CTDH = new ChiTietDonHang();
        ResultSet rs = dbconn.getData(sql);
        ArrayList<ChiTietDonHang> result = new ArrayList<>();
        while (rs.next()) {
            CTDH.setId(rs.getInt("id"));
            CTDH.setMaHang(rs.getInt("ma_hang"));
            CTDH.setSLNhap(rs.getInt("soluong_nhap"));
            CTDH.setMaDon(rs.getInt("ma_don"));
            result.add(CTDH);
        }
        return result;
    }
}
