package Repository;

import DataConnect.DataConnection;
import Model.ChiTietKho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChiTietKhoRepository {

    DataConnection dbconn;
    Connection conn;

    public ChiTietKhoRepository(DataConnection dconn) {
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }

    ArrayList<ChiTietKho> listCTK = new ArrayList<>();

    public ArrayList<ChiTietKho> getAll() throws SQLException {
        String sql = "Select * from [ChiTietKho]";

        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            ChiTietKho CTK = new ChiTietKho();
            CTK.setId(rs.getInt("id"));
            CTK.setMakho(rs.getInt("ma_kho"));
            CTK.setMaHang(rs.getInt("ma_hang"));
            CTK.setSL(rs.getInt("so_luong"));
            listCTK.add(CTK);
        }

        return listCTK;
    }

    public boolean insert(ChiTietKho CTK) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "INSERT INTO [ChiTietKho] (ma_kho, ma_hang, so_luong) VALUES(" + CTK.getMaKho() + ""
                + "," + CTK.getMaHang() + "," + CTK.getSL() + ")";
        boolean is;
        is = stm.executeUpdate(sql) > 0;

        dbconn.Close();
        return is;
    }

    public boolean update(ChiTietKho CTK) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "UPDATE [ChiTietKho] SET ma_kho =" + CTK.getMaKho() + ", ma_hang = " + CTK.getMaHang() + ", "
                + "so_luong = " + CTK.getSL() + " where id = " + CTK.getId();
        boolean is;
        is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [ChiTietKho] where id = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public ChiTietKho getbyId(int id) throws SQLException {

        String sql = "Select * from [ChiTietKho] where id = " + id;
        ChiTietKho ctk = new ChiTietKho();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            ctk.setId(rs.getInt("id"));
            ctk.setMakho(rs.getInt("ma_kho"));
            ctk.setMaHang(rs.getInt("ma_hang"));
            ctk.setSL(rs.getInt("so_luong"));
        }
        return ctk;
    }

    public ArrayList<ChiTietKho> getbyStockId(int id) throws SQLException {

        String sql = "Select * from [ChiTietKho] where ma_kho = " + id;
        ArrayList<ChiTietKho> a = new ArrayList<ChiTietKho>();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            ChiTietKho ctk = new ChiTietKho();
            ctk.setId(rs.getInt("id"));
            ctk.setMakho(rs.getInt("ma_kho"));
            ctk.setMaHang(rs.getInt("ma_hang"));
            ctk.setSL(rs.getInt("so_luong"));
            a.add(ctk);
        }
        return a;
    }

    public ChiTietKho checkExist(int id_sua, int id) throws SQLException {
        String sql = "Select * from [ChiTietKho] where ma_kho = " + id + " and id = " + id_sua;
        ResultSet rs = dbconn.getData(sql);
        ChiTietKho a = new ChiTietKho();
        while (rs.next()) {
            a.setId(rs.getInt("id"));
            a.setMakho(rs.getInt("ma_kho"));
            a.setMaHang(rs.getInt("ma_hang"));
            a.setSL(rs.getInt("so_luong"));

        }
        return a;
    }
}
