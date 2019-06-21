package Repository;

import DataConnect.DataConnection;
import Model.DonHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DonHangRepository {

    DataConnection dbconn;
    Connection conn;

    public DonHangRepository(DataConnection dconn) {
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }

    ArrayList<DonHang> listDH = new ArrayList<>();

    public ArrayList<DonHang> getAll() throws SQLException {
        String sql = "Select * from [DonHang]";

        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            DonHang DH = new DonHang();
            DH.setId(rs.getInt("id"));
            DH.setMaThuKho(rs.getInt("ma_thukho"));
            DH.setNgayNhap(rs.getString("ngay_nhap"));
            DH.setLoaiPhieu(rs.getInt("loai_phieu"));
            listDH.add(DH);
        }

        return listDH;
    }

    public int insert(DonHang DH) throws SQLException {
//        Statement stm = conn.createStatement();
//        String sql = "INSERT INTO [DonHang] (ma_thukho, ngay_nhap, loai_phieu) VALUES"
//                + "("+ DH.getMaThuKho() +",'"+ DH.getNgayNhap() +"',"+ DH.getLoaiPhieu() +")";
//        boolean is;
//        is = stm.executeUpdate(sql) > 0;
//        dbconn.Close();
//        return is;
        String sql = "INSERT INTO [DonHang] (ma_thukho, ngay_nhap, loai_phieu) VALUES (?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, DH.getMaThuKho());
        preparedStatement.setString(2, DH.getNgayNhap());
        preparedStatement.setInt(3, DH.getLoaiPhieu());
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        int id = 0;
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
        return id;
    }

    public boolean update(DonHang DH) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "UPDATE [DonHang] SET ma_thukho = " + DH.getMaThuKho() + " , ngay_nhap = '" + DH.getNgayNhap() + "' ,"
                + " loai_phieu = " + DH.getLoaiPhieu() + " where id = " + DH.getId();
        boolean is;
        is = stm.executeUpdate(sql) > 0;
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

        String sql = "Select * from [DonHang] where id = " + id;
        DonHang DH = new DonHang();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            DH.setId(rs.getInt("id"));
            DH.setMaThuKho(rs.getInt("ma_thukho"));
            DH.setNgayNhap(rs.getString("ngay_nhap"));
            DH.setLoaiPhieu(rs.getInt("loai_phieu"));
        }
        return DH;
    }
}
