package Repository;

import DataConnect.DataConnection;
import Model.NhomHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhomHangRepository {

    DataConnection dbconn;
    Connection conn;

    public NhomHangRepository(DataConnection dconn) {
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }

    ArrayList<NhomHang> listNH = new ArrayList<>();

    public ArrayList<NhomHang> getAll() throws SQLException {
        String sql = "Select * from [NhomHang]";

        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            NhomHang NH = new NhomHang();
            NH.setMaNhom(rs.getInt("ma_nhom"));
            NH.setTenNhom(rs.getString("ten_nhom"));
            listNH.add(NH);
        }
        // Đóng Connect

        return listNH;
    }

    public boolean insert(NhomHang NH) throws SQLException {
        String sql = "INSERT INTO [NhomHang] VALUES(?,?)";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, NH.getMaNhom());
            stm.setString(2, NH.getTenNhom());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean update(NhomHang NH) throws SQLException {
        String sql = "UPDATE [NhomHang] SET ma_nhom = ? , ten_nhom = ?";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, NH.getMaNhom());
            stm.setString(2, NH.getTenNhom());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [NhomHang] where id = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public NhomHang getbyId(int id) throws SQLException {

        String sql = "Select * from [NhomHang] where id = " + id;
        NhomHang NH = new NhomHang();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            NH.setMaNhom(rs.getInt("ma_NH"));
            NH.setTenNhom(rs.getString("ten_NH"));
        }
        return NH;
    }
}
