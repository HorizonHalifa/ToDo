package org.example.todo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

public class ToDoItem {
    private String name;
    private int id;
    private boolean isDone = false;

    private boolean isSelected = false;

    public ToDoItem(String name, int id) throws IOException {
        if(name == null) {
            throw(new IOException("Name of ToDoItem can not be empty."));
        }
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean isDoneProperty() {
        return isDone;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean b) {
        this.isSelected = b;
    }
}
