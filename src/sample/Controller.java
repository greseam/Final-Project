package sample;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.ClosePath;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.*;
import javafx.beans.property.ListProperty;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ListView;



import javax.swing.*;

public class Controller {
    @FXML
    public Label descriptionText;
    public Label BullsNum;
    public Label CowsNum;
    public Label scoreID;
    public Label guessID;
    public Label Timer;
    public Button playButton;
    public Button checkerButton;
    public ListView history;
    @FXML
    private TextField guessTextBox;

    methods Run = new methods();
     String[] contentList;//format helper
    ArrayList<String> historyList = new ArrayList<>();
    ListProperty<String> listProperty = new SimpleListProperty<>();

    @FXML
    //play button and rules
    public void Start(ActionEvent actionEvent) throws IOException, InterruptedException {
        playButton.setVisible(false);
        Run.generateNewStages();
    }

    //event handler for the game guess button
    public void Makeguess(ActionEvent actionEvent) throws IOException, InterruptedException {
                String content = null;
                content = Run.makeGuess(guessTextBox.getText());
                contentList = content.split(",");
                checkerButton.setText(contentList[0]);
                BullsNum.setText(contentList[1]);
                CowsNum.setText(contentList[2]);
                descriptionText.setText(contentList[3]);
                scoreID.setText(contentList[5]);
                guessID.setText(contentList[4]);
                try {
                    historyList.add(contentList[7]+"  B>>"+contentList[1]+"  C>>"+contentList[2]);
                }catch (Exception e){
                }
                listProperty.set(FXCollections.observableArrayList(historyList));
                history.itemsProperty().bind(listProperty);
                if (Boolean.valueOf(contentList[6])==true || Integer.valueOf(contentList[4])<0){
                    historyList.clear();
                }

    }
    //event handler methods for scoreboard
    public void clearList(ActionEvent actionEvent) {

    }

    public void clearSelected(ActionEvent actionEvent) {
    }

    public void addPlayer(ActionEvent actionEvent) {
    }

}
