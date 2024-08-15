package org.example.todo;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ToDoController {

    @FXML
    private GridPane root;
    private ToDoItem item;
    private int counter = 0;
    private TreeMap<Integer, ToDoItem> map = new TreeMap<>(); // Database to store our definitions.

    private ToDoRepository repository  = new ToDoRepository("jdbc:sqlite:todo.sqlite");

    @FXML
    private VBox vbox;

    public void initialize() {
        populateData();
        // TODO: handleItemClicks
    }

    private void populateData() {
        for(Map.Entry<Integer, ToDoItem> entry : map.entrySet()) {
            CheckBox checkBox = new CheckBox();
            root.add(checkBox, 0, entry.getKey());
            root.add(new Label(entry.getValue().toString()), 1, entry.getKey());
        }
    }

    @FXML
    void addPressed(ActionEvent event) throws IOException { // if the button is pressed, initiate process to add an item
        String toAdd = JOptionPane.showInputDialog("Add a ToDo Item");
        ToDoItem newItem = new ToDoItem(toAdd, counter);
        map.put(counter, newItem);
        counter++;
        repository.createToDo(newItem);
        populateData();
    }

    @FXML
    void savePressed(ActionEvent event) {

    }

    @FXML
    void handleItemClicks(KeyEvent event) {

    }
    /*
    void handleItemClicks() { // when a user clicks we want to be able to edit the item they clicked if they click nothing we ignore
        root.setOnMouseClicked(event -> {
            ToDoItem selectedItem = root.getSelectionModel().getSelectedItem(); // get what the user clicked
            if (selectedItem == null) { // user clicked nothing -> ignore
                return;
            }
            System.out.println(selectedItem);
            int toRemoveKey = selectedItem.getId();
            String action = JOptionPane.showInputDialog("To delete item, type '1' + \n To edit the item type '2'");

            int actionNum = Integer.parseInt(action);
            switch (actionNum) {
                case 1:
                    map.remove(toRemoveKey);
                    Dialog<?> d2 = new Alert(Alert.AlertType.CONFIRMATION, "Item has been removed");
                    d2.show();
                    break;
                case 2:
                    String newName = JOptionPane.showInputDialog("Please provide a new item.");
                    map.remove(toRemoveKey);
                    map.put(toRemoveKey, new ToDoItem(newName, toRemoveKey));
                    break;
                default:
                    Dialog<?> d1 = new Alert(Alert.AlertType.ERROR, "Your input must have been '1' or '2'.");
                    d1.show();
            }
            populateData();
        });
    }*/


}



