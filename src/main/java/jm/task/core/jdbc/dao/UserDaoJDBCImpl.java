package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnectJDBC;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() { }

    Connection connection = getConnectJDBC();

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR (30) NOT NULL, " +
                "lastName VARCHAR (30) NOT NULL, " +
                "age int NOT NULL" +
                ") ENGINE=InnoDB;";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Table users create");
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS users";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Delete users");
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем – " + name + " добавлен в базу данных ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            
            System.out.println("Пользователь с id " + id + " удалён из таблицы");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String SQL = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = connection.createStatement().executeQuery(SQL)){
            //ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);

                users.add(new User(name, lastName, age));
            }
            System.out.println("сейчас будет вывод");
        } catch (SQLException throwables) {
        }
        return users;
    }

    public void cleanUsersTable() {
        String SQL = "DELETE FROM users";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Clean users");
    }
}
