package Function;

import DataConnect.DataConnection;
import Model.Admin;
import Repository.AdminRepository;
import java.sql.SQLException;

public class LoginApp {

    public static String login(String username, String password) throws SQLException {
        DataConnection dataConn = new DataConnection();
        AdminRepository adRepository = new AdminRepository(dataConn);
        Admin user = adRepository.getAdmin(username, password);
        if(user.getRole() == null){
            return "Sai tai khoan hoac mat khau, vui long thu lai";
        }
        else
            return user.getRole();
    }
}
