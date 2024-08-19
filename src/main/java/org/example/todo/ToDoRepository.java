package org.example.todo;

import java.sql.*;
import java.util.TreeMap;

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

    public void deleteTodo(ToDoItem item) {
        String sql = "DELETE FROM ToDo WHERE id = ?";
        try( Connection connection = DriverManager.getConnection(connectionString);
             var statement = connection.prepareStatement(sql) ) {
            statement.setInt(1, item.getId());
            statement.execute();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public TreeMap<Integer, ToDoItem> loadTodo() {
        String sql = "SELECT name, id, is_done FROM ToDo";
        TreeMap<Integer, ToDoItem> map = new TreeMap<>();

        try( Connection connection = DriverManager.getConnection(connectionString);
             var statement = connection.prepareStatement(sql) ) {
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                String name = rs.getString(1);
                int id = rs.getInt(2);
                int isDone = rs.getInt(3);
                boolean isDoneBool = false;
                if(isDone == 1) {isDoneBool = true;}

                ToDoItem toAdd = new ToDoItem(name, id, isDoneBool);
                map.put(id, toAdd);
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return map;
    }

}
