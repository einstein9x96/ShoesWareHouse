package Repository;

import DataConnect.DataConnection;
import Model.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public boolean insert(Admin user) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "INSERT INTO [Users] (ten, user_name, password, role) VALUES('" + user.getTen() +"','" + user.getUserName()+"',"
                + "'" + user.getPassword()+"','" + user.getRole()+"')";
        boolean is = stm.executeUpdate(sql) > 0;
        dbconn.Close();
        return is;
    }

    public boolean update(Admin user) throws SQLException {
        Statement stm = conn.createStatement();
        String sql = "UPDATE [Users] SET ten = '" + user.getTen() +"' , user_name = '" + user.getUserName()+"'"
                + " , password = '" + user.getPassword()+"' , role = '" + user.getRole()+"' where id = " + user.getId();
        boolean is = stm.executeUpdate(sql) > 0;
//        dbconn.Close();
        return is;
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

    public boolean isExistUsername(String username) throws SQLException {
        String sql = "Select * from [Users] where user_name = " + "'" + username + "'";
        Admin user = new Admin();
        ResultSet rs = dbconn.getData(sql);
        return rs.next();
    }

    public Admin findByUsername(String username) throws SQLException {
        String sql = "Select * from [Users] where user_name like " + "'%" + username + "%'";
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
