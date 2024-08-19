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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ToDoController {

    @FXML
    private GridPane root;
    private ToDoItem item;
    private int counter = 0;
    private TreeMap<Integer, ToDoItem> map = new TreeMap<>(); // Database to store our items

    private TreeMap<Integer, ToDoItem> selectedMap = new TreeMap<>(); // Database to store the selected items.

    private ToDoRepository repository  = new ToDoRepository("jdbc:sqlite:todo.sqlite");

    @FXML
    private VBox vbox;

    public void initialize() {
        populateData();
    }

    private void populateData() {
        for(Map.Entry<Integer, ToDoItem> entry : map.entrySet()) {
            CheckBox checkBox = new CheckBox();
            root.add(checkBox, 0, entry.getKey());
            root.add(new Label(entry.getValue().toString()), 1, entry.getKey());
        }

        // Handle mouse overs
        for (Node node : root.getChildren()) {
            // Highlight selected nodes and keep track of which nodes are being selected.
            node.setOnMouseClicked(e ->  {
                Integer targetIndex = GridPane.getColumnIndex(node);
                if (GridPane.getColumnIndex(node) == targetIndex) {
                    int row = GridPane.getRowIndex(node);
                    ToDoItem selected = map.get(row);
                    if(selected.isSelected()){ // The item is already selected, so unselect it.
                        node.setStyle("-fx-background-color:#eaeaea;");
                        selected.setSelected(false);
                        selectedMap.remove(row);
                    } else { // The item is not yet selected, select it.
                        node.setStyle("-fx-background-color:#ffc584;");
                        selected.setSelected(true);
                        selectedMap.put(row, selected);
                    }
                }
            });
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
    void deletePressed(ActionEvent event) {
        for(Map.Entry<Integer, ToDoItem> entry : selectedMap.entrySet()) {
            map.remove(entry.getKey());
            root.getChildren().clear();
            populateData();
        }
    }

}



