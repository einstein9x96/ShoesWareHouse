package Repository;

import DataConnect.DataConnection;
import Model.ThuKho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ThuKhoRepository {

    DataConnection dbconn;
    Connection conn;

    public ThuKhoRepository(DataConnection dconn) {
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }

    ArrayList<ThuKho> listTK = new ArrayList<>();

    public ArrayList<ThuKho> getAll() throws SQLException {
        String sql = "Select * from [ThuKho]";

        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            ThuKho TK = new ThuKho();
            TK.setID(rs.getInt("id"));
            TK.setThuKho(rs.getString("ten_thukho"));
            TK.setMaKho(rs.getInt("ma_kho"));
            listTK.add(TK);
        }
        // Đóng Connect

        return listTK;
    }

    public boolean insert(ThuKho TK) throws SQLException {
        String sql = "INSERT INTO [ThuKho] VALUES(?,?,?)";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, TK.getID());
            stm.setString(2, TK.getThuKho());
            stm.setInt(3, TK.getMaKho());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean update(ThuKho TK) throws SQLException {
        String sql = "UPDATE [ThuKho] SET id = ? , ten_thukho = ? , ma_kho = ?";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, TK.getID());
            stm.setString(2, TK.getThuKho());
            stm.setInt(3, TK.getMaKho());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [ThuKho] where id = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public ThuKho getbyId(int id) throws SQLException {

        String sql = "Select * from [ThuKho] where id = " + id;
        ThuKho TK = new ThuKho();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            TK.setID(rs.getInt("id"));
            TK.setThuKho(rs.getString("ten_thukho"));
            TK.setMaKho(rs.getInt("ma_kho"));
        }
        return TK;
    }
}
