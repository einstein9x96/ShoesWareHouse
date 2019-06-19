package Repository;

import DataConnect.DataConnection;
import Model.NhomHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        return listNH;
    }

    public boolean insert(NhomHang NH) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "INSERT INTO [NhomHang] (ten_nhom) VALUES('" + NH.getTenNhom() + "')";
        boolean is;
        is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean update(NhomHang group) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "UPDATE [NhomHang] SET ten_nhom = '" + group.getTenNhom() + "' where ma_nhom = " + group.getMaNhom();
        boolean is = stm.executeUpdate(sql) > 0;
//        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        deletegoods(id);

        String sql = "DELETE FROM [NhomHang] where ma_nhom = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;

    }

    public void deletegoods(int id) throws SQLException {
        String sql = "DELETE FROM [HangHoa] where ma_nhom = " + id;

        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.executeUpdate();
        }
    }

    public NhomHang getbyId(int id) throws SQLException {

        String sql = "Select * from [NhomHang] where ma_nhom = " + id;
        NhomHang NH = new NhomHang();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            NH.setMaNhom(rs.getInt("ma_nhom"));
            NH.setTenNhom(rs.getString("ten_nhom"));
        }
        return NH;
    }
}
