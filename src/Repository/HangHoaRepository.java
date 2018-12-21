package Repository;

import DataConnect.DataConnection;
import Model.HangHoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HangHoaRepository {
    DataConnection dbconn;
    Connection conn;
    
    public HangHoaRepository(DataConnection dconn ){
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }
    
    ArrayList<HangHoa> listHH = new ArrayList<>();
    
    public ArrayList<HangHoa> getAll() throws SQLException{
        String sql = "Select * from [HangHoa]";
        
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            HangHoa HH = new HangHoa();
            HH.setMaHang(rs.getInt("ma_hang"));
            HH.setMaNhom(rs.getInt("ma_nhom"));
            HH.setTenHang(rs.getString("ten_hang"));
            HH.setDVT(rs.getString("dvt"));
            listHH.add(HH);
        }
        // Đóng Connect
       
        return listHH;
    }

    public boolean insert(HangHoa HH) throws SQLException {
        String sql = "INSERT INTO [HangHoa] VALUES(?,?,?,?)";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, HH.getMaHang());
            stm.setInt(2, HH.getMaNhom());
            stm.setString(3, HH.getTenHang());
            stm.setString(4, HH.getDVT());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean update(HangHoa HH) throws SQLException {
        String sql = "UPDATE [HangHoa] SET ma_hang = ? , ma_nhom = ? , ten_hang = ? , dvt = ? ";
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, HH.getMaHang());
            stm.setInt(2, HH.getMaNhom());
            stm.setString(3, HH.getTenHang());
            stm.setString(4, HH.getDVT());
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [HangHoa] where id = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public HangHoa getbyId(int id) throws SQLException {
        
        String sql = "Select * from [HangHoa] where id = " + id ;
        HangHoa HH = new HangHoa();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            HH.setMaHang(rs.getInt("ma_hang"));
            HH.setMaNhom(rs.getInt("ma_nhom"));
            HH.setTenHang(rs.getString("ten_hang"));
            HH.setDVT(rs.getString("dvt"));
        }
        return HH;
    }
}
