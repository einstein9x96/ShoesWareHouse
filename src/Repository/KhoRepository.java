package Repository;

import DataConnect.DataConnection;
import Model.Kho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class KhoRepository {
    DataConnection dbconn;
    Connection conn;
    
    public KhoRepository(DataConnection dconn ){
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }
    
    ArrayList<Kho> listHH = new ArrayList<>();
    
    public ArrayList<Kho> getAll() throws SQLException{
        String sql = "Select * from [Kho]";
        
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            Kho HH = new Kho();
            HH.setMaHang(rs.getInt("ma_hang"));
            HH.setMaNhom(rs.getInt("so_luong"));
            HH.setTenHang(rs.getString("ten_hang"));
            HH.setDVT(rs.getString("dvt"));
            listHH.add(HH);
        }
        return listHH;
    }

    public boolean insert(Kho HH) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "INSERT INTO [HangHoa] (ma_nhom, ten_hang, dvt) "
                + "VALUES("+ HH.getSoLuong()+",'"+ HH.getTenHang() +"','"+ HH.getDVT() +"')";
        boolean is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean update(Kho HH) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "UPDATE [Kho] SET ma_nhom = "+ HH.getSoLuong()+" , ten_hang = '"+ HH.getTenHang() +"' , "
                + "dvt = '"+ HH.getDVT() +"' where ma_hang = " + HH.getMaHang();
        boolean is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [Kho] where ma_hang = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }

    public Kho getbyId(int id) throws SQLException {
        
        String sql = "Select * from [Kho] where ma_hang = " + id ;
        Kho HH = new Kho();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            HH.setMaHang(rs.getInt("ma_hang"));
            HH.setMaNhom(rs.getInt("so_luong"));
            HH.setTenHang(rs.getString("ten_hang"));
            HH.setDVT(rs.getString("dvt"));
        }
        return HH;
    }
}
