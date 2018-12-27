package Repository;

import DataConnect.DataConnection;
import Model.HangHoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        return listHH;
    }

    public boolean insert(HangHoa HH) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "INSERT INTO [HangHoa] (ma_nhom, ten_hang, dvt) "
                + "VALUES("+ HH.getMaNhom() +",'"+ HH.getTenHang() +"','"+ HH.getDVT() +"')";
        boolean is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean update(HangHoa HH) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "UPDATE [HangHoa] SET ma_nhom = "+ HH.getMaNhom() +" , ten_hang = '"+ HH.getTenHang() +"' , "
                + "dvt = '"+ HH.getDVT() +"' where ma_hang = " + HH.getMaHang();
        boolean is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [HangHoa] where ma_hang = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public HangHoa getbyId(int id) throws SQLException {
        
        String sql = "Select * from [HangHoa] where ma_hang = " + id ;
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
