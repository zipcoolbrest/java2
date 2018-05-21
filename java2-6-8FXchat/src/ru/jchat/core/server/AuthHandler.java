package ru.jchat.core.server;

import java.sql.*;

public class AuthHandler {

    private Connection connection;
    private Statement statement;
    private PreparedStatement psFindUser;
    private PreparedStatement psUserRegistration;

    public void connect() throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        statement = connection.createStatement();
        checkTable();
        psFindUser = connection.prepareStatement("SELECT nick FROM users WHERE login = ? and password = ?;");
        psUserRegistration = connection.prepareStatement("INSERT INTO users (login, password, nick) VALUES (?, ?, ?)");
//        for (int i = 1; i < 5; i++) {
////            userRegistration("login"+i, "pass"+i, "nick"+i);
////        }
    }

    public void checkTable(){
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "login TEXT UNIQUE, password TEXT, nick TEXT UNIQUE);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userRegistration(String login, String pass, String nick)throws SQLException{
        int passHash = pass.hashCode();
        try {
            psUserRegistration.setString(1, login);
            psUserRegistration.setInt(2,passHash);
            psUserRegistration.setString(3, nick);
            return  psUserRegistration.executeUpdate() == 1;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new SQLException("Ошибка регистрации. Не удается записать данные в БД");
        }
    }

    public String getNickByLoginAndPass(String login, String pass){
        int passHash = pass.hashCode();
        try {
            psFindUser.setString(1, login);
            psFindUser.setInt(2, passHash);
            ResultSet resultSet = psFindUser.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("nick");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect(){
        try {
            //statement.close();
            psFindUser.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
