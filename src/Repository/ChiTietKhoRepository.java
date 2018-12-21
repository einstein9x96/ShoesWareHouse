package Repository;

import DataConnect.DataConnection;
import Model.ChiTietKho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietKhoRepository {
    DataConnection dbconn;
    Connection conn;
    
    public ChiTietKhoRepository(DataConnection dconn ){
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }
    
    ArrayList<ChiTietKho> listCTK = new ArrayList<>();
    
    public ArrayList<ChiTietKho> getAll() throws SQLException{
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
        // Đóng Connect
       
        return listCTK;
    }

    public boolean insert(ChiTietKho CTK) throws SQLException {
        String sql = "INSERT INTO [ChiTietKho] VALUES(?,?,?,?)";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, CTK.getId());
            stm.setInt(2, CTK.getMaKho());
            stm.setInt(3, CTK.getMaHang());
            stm.setInt(4, CTK.getSL());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean update(ChiTietKho CTK) throws SQLException {
        String sql = "UPDATE [ChiTietKho] SET id = ? , ma_kho = ? , ma_hang = ? , so_luong = ? ";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, CTK.getId());
            stm.setInt(2, CTK.getMaKho());
            stm.setInt(3, CTK.getMaHang());
            stm.setInt(4, CTK.getSL());
            is = stm.executeUpdate() > 0;
        }
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
        
        String sql = "Select * from [ChiTietKho] where id = " + id ;
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
}
