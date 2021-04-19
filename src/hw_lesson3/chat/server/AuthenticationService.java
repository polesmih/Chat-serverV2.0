package hw_lesson3.chat.server;

import java.sql.*;

public class AuthenticationService {

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/network_chat_test",
                    "root",
                    "2240512");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static String getNameByLoginAndPassword(String login, String password) {

        String loginStr = ("select * from users where login = ? and password = ?");

        try (PreparedStatement psAuth = getConnection().prepareStatement(loginStr)) {
            psAuth.setString(1, login);
            psAuth.setString(2, password);
            ResultSet resultSet = psAuth.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
