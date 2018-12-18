package Repository;

import DataConnect.DataConnection;
import Model.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminRepository {

    DataConnection dbconn;
    Connection conn;

    public AdminRepository(DataConnection dconn) {
        this.dbconn = dconn;
        this.conn = dconn.getConnectionToMSSQL();
    }

    public ArrayList<Admin> GetAll() throws SQLException {
        ArrayList<Admin> listAdmin = new ArrayList<>();

        String sql = "Select * from [Users]";

        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            Admin User = new Admin();
            User.setId(rs.getInt("id"));
            User.setTen(rs.getString("ten"));
            User.setUserName(rs.getString("user_name"));
            User.setPassword(rs.getString("password"));
            User.setRole(rs.getString("role"));

            listAdmin.add(User);
        }
        return listAdmin;
    }

    public Admin getAdmin(String username, String password) throws SQLException {

        String sql = "Select * from [Users] where user_name = '" + username + "' and password = '" + password + "'";
        Admin user = new Admin();
        ResultSet rs = dbconn.getData(sql);
        if (rs == null) {
            return user;
        }
        while (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setTen(rs.getString("ten"));
            user.setUserName(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
        }
        return user;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM [Users] where id = " + id;
        boolean is;
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            is = stm.executeUpdate() > 0;
        }
        dbconn.Close();
        return is;
    }
    
    public Admin getbyId(int id) throws SQLException {

        String sql = "Select * from [Users] where id = " + id;
        Admin user = new Admin();
        ResultSet rs = dbconn.getData(sql);
        while (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setTen(rs.getString("ten"));
            user.setUserName(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
        }
        return user;
    }
}
