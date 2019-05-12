package sample;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

import java.io.IOException;
import java.util.ArrayList;


import javax.swing.*;
import javax.swing.text.TableView;
import javax.swing.text.html.ListView;

public class Controller {
    @FXML
    public Label descriptionText;
    public Label BullsNum;
    public Label CowsNum;
    public Label scoreID;
    public Label guessID;
    public Label Timer;
    @FXML
    public Button checkerButton;
    @FXML
    private TextField guessTextBox;

    methods Run = new methods();
     String[] contentList;

    @FXML
    public void Start(ActionEvent actionEvent) throws IOException, InterruptedException {
        Run.generateNewStages();
    }

    public void Makeguess(ActionEvent actionEvent) throws IOException, InterruptedException {
                String content = null;
                content = Run.makeGuess(guessTextBox.getText());
                contentList = content.split(",");
                checkerButton.setText(contentList[0]);
                BullsNum.setText(contentList[1]);
                CowsNum.setText(contentList[2]);
                scoreID.setText(contentList[5]);
                guessID.setText(contentList[4]);

    }

    public void clearList(ActionEvent actionEvent) {

    }

    public void clearSelected(ActionEvent actionEvent) {
    }

    public void addPlayer(ActionEvent actionEvent) {
    }

}
