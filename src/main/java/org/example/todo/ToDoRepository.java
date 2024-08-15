package org.example.todo;

import java.sql.*;

public class ToDoRepository {

    private String connectionString;

    public ToDoRepository(String connectionString){
        this.connectionString = connectionString;
    }

    public void createToDo(ToDoItem item)  {
        String sql = "INSERT INTO ToDo(name) VALUES(?)";
        try( Connection connection = DriverManager.getConnection(connectionString);
             var statement = connection.prepareStatement(sql) ) {
            statement.setString(1, item.toString());
            statement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
