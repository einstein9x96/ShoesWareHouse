package Repository;

import DataConnect.DataConnection;
import Model.ChiTietDonHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO [ChiTietDonHang] VALUES(?,?,?,?)";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, CTDH.getId());
            stm.setInt(2, CTDH.getMaHang());
            stm.setInt(3, CTDH.getSLNhap());
            stm.setInt(4, CTDH.getMaDon());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean update(ChiTietDonHang CTDH) throws SQLException {
        String sql = "UPDATE [ChiTietDonHang] SET id = ? , ma_hang = ? , soluong_nhap = ? , ma_don = ? ";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, CTDH.getId());
            stm.setInt(2, CTDH.getMaHang());
            stm.setInt(3, CTDH.getSLNhap());
            stm.setInt(4, CTDH.getMaDon());
            is = stm.executeUpdate() > 0;
        }
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
}
