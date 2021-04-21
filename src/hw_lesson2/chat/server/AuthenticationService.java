package hw_lesson2.chat.server;

import java.sql.*;

public class AuthenticationService {

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/network_chat_test");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

//аутентификация

    public static String getNameByLoginAndPassword(String login, String password) {

        String loginStr = ("select * from network_chat_test where login = ? and password = ?");

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

//регистрация

    public static boolean registration(String name, String login, String password) {
        String checkLogin = ("select * from network_chat_test where login = ?");
        String checkName = ("select * from network_chat_test where name = ?");
        String reg = ("insert into network_chat_test (name, login, password) values (?, ?, ?)");

        try (PreparedStatement psCheckLogin = getConnection().prepareStatement(checkLogin)) {
            psCheckLogin.setString(2, login);
            ResultSet rsLoginCheck = psCheckLogin.executeQuery();

            if (rsLoginCheck.next()) {
                return false;
            } else {

                try (PreparedStatement psCheckNick = getConnection().prepareStatement(checkName)) {
                    psCheckNick.setString(1, name);
                    ResultSet rsCheckNick = psCheckNick.executeQuery();

                    if (rsCheckNick.next()) {
                        return false;
                    } else {

                        try (PreparedStatement psRegistration = getConnection().prepareStatement(reg)) {
                            psRegistration.setString(1, name);
                            psRegistration.setString(2, login);
                            psRegistration.setString(3, password);

                            psRegistration.executeUpdate();
                            return true;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
