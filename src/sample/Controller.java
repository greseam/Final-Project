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
import javafx.collections.ObservableList;
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
import java.util.Comparator;
import java.util.List;
import javafx.scene.control.ListView;



import javax.swing.*;
import javax.swing.text.TableView;

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
    public ListView nameList;
    public ListView scoreIDlist;
    public ListView posList;
    @FXML
    private TextField guessTextBox;

    methods Run = new methods();
    int Position = 0;
     String[] contentList;//format helper
    ArrayList<String> historyList = new ArrayList<>();
    ArrayList<String> scoreArray = new ArrayList<>();
    ArrayList<String> initials = new ArrayList<>();
    ArrayList<String> positions = new ArrayList<>();
    ArrayList<Player> data = new ArrayList<>();
    ListProperty<String> listProperty = new SimpleListProperty<>();
    ListProperty<String> scoreProperty = new SimpleListProperty<>();
    ListProperty<String> nameProperty = new SimpleListProperty<>();
    ListProperty<String> posProperty = new SimpleListProperty<>();


    @FXML
    //play button and rules
    public void Start(ActionEvent actionEvent) throws IOException, InterruptedException {
        playButton.setVisible(false);
        Run.generateNewStages();
    }

    //event handler for the game guess button
    public int Makeguess(ActionEvent actionEvent) throws IOException, InterruptedException {
                String content = null;
                content = Run.makeGuess(guessTextBox.getText(),true);
                contentList = content.split(",");
                checkerButton.setText(contentList[0]);
                BullsNum.setText(contentList[1]);
                CowsNum.setText(contentList[2]);
                descriptionText.setText(contentList[3]);
                scoreID.setText(contentList[5]);
                guessID.setText(contentList[4]);
                try {
                    historyList.add(contentList[7]+"  B>> "+contentList[1]+"  C>> "+contentList[2]);
                }catch (Exception e){
                }
                listProperty.set(FXCollections.observableArrayList(historyList));
                history.itemsProperty().bind(listProperty);
                if (Boolean.valueOf(contentList[6])==true || Integer.valueOf(contentList[4])<0){
                    historyList.clear();
                }
        return Integer.valueOf(contentList[5]);
    }
    //event handler methods for scoreboard
    public void clearList(ActionEvent actionEvent) {
        clearData(initials,nameList);
        clearData(scoreArray,scoreIDlist);
        clearData(positions,posList);
        Position = 0;
        data.clear();
        //add a removeall from database method
    }



    public void addPlayer(ActionEvent actionEvent) throws IOException, InterruptedException {
        //add method that adds current score and prompts for initials
        int finalScore = Integer.valueOf(Run.makeGuess(null,false));

        Position =1;
        String initials1 = JOptionPane.showInputDialog(null,"please enter your initials","Initials",3);
        for (int i = 0; i < data.size(); i++) {
            Position++;
        }
        Player player = new Player(initials1,finalScore);
        data.add(player);
        setupData(player.returnInitials(),initials,nameList,nameProperty);
        setupData(String.valueOf(player.returnScore()),scoreArray,scoreIDlist,scoreProperty);
        setupData(String.valueOf(Position),positions,posList,posProperty);

        //foreach player set position to last position + 1

    }

    public void clearData(ArrayList List,ListView View){
        listProperty.set(FXCollections.observableArrayList(List));
        View.itemsProperty().bind(listProperty);
            List.clear();
    }
    public void setupData(String content, ArrayList<String > List,ListView View,ListProperty listProperty){
        try {
            List.add(content);
        }catch (Exception e){
        }
        listProperty.set(FXCollections.observableArrayList(List));
        View.itemsProperty().bind(listProperty);
    }

}
