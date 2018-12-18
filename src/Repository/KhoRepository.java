package Repository;

import DataConnect.DataConnection;
import Model.Kho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhoRepository {

    DataConnection dbconn;
    Connection conn;

    public KhoRepository(DataConnection dconn) {
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }

    ArrayList<Kho> listkho = new ArrayList<>();

    public ArrayList<Kho> getAll() throws SQLException {
        String sql = "Select * from [Kho]";

        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            Kho kho = new Kho();
            kho.setMaKho(rs.getInt("ma_kho"));
            kho.setTenKho(rs.getString("ten_kho"));
            kho.setDiaDiem(rs.getString("dia_diem"));
            listkho.add(kho);
        }
        // Đóng Connect

        return listkho;
    }

    public boolean insert(Kho kho) throws SQLException {
        String sql = "INSERT INTO [Kho] VALUES(?,?,?)";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, kho.getMaKho());
            stm.setString(2, kho.getTenKho());
            stm.setString(3, kho.getDiaDiem());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean update(Kho kho) throws SQLException {
        String sql = "UPDATE [Kho] SET ma_kho = ? , ten_kho = ? , dia_diem = ?";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, kho.getMaKho());
            stm.setString(2, kho.getTenKho());
            stm.setString(3, kho.getDiaDiem());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [Kho] where id = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public Kho getbyId(int id) throws SQLException {

        String sql = "Select * from [Kho] where id = " + id;
        Kho kho = new Kho();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            kho.setMaKho(rs.getInt("ma_kho"));
            kho.setTenKho(rs.getString("ten_kho"));
            kho.setDiaDiem(rs.getString("dia_diem"));
        }
        return kho;
    }
}
