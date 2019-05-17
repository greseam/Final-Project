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
import java.util.stream.Stream;

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
    public Label roundNum;
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

    ObservableList<Player> playerObjects = FXCollections.observableArrayList();
    ObservableList<Player> playerObjectsNew = FXCollections.observableArrayList();



    @FXML
    //play button and rules
    public void Start(ActionEvent actionEvent) throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        playButton.setVisible(false);
        Run.generateNewStages();
        playerObjects =  Run.retreiveScoreBoardData(null);
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
                roundNum.setText(String.valueOf(Integer.valueOf(contentList[9])));
                if (Integer.valueOf(contentList[9]) < 0){roundNum.setText("Waiting on next game"); checkerButton.setText("Start new game");}
                try {
                playerObjectsNew.add(new Player(contentList[8], Integer.valueOf(contentList[5])));
                }catch (Exception e){}
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
    public void clearList(ActionEvent actionEvent) throws SQLException {
        int selectedOption;
        selectedOption = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete ALL data in the Scoreboard?",
                "Choose",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            clearData(initials, nameList);
            clearData(scoreArray, scoreIDlist);
            clearData(positions, posList);
            Position = 0;
            data.clear();
            //add a removeall from database method
            Run.RemoveAllFromDatabase(null, null);
        }
        else { JOptionPane.showMessageDialog(null,"No action taken\nReturning to Scoreboard!");}
    }



    public void addPlayer(ActionEvent actionEvent) throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        playerObjects =  Run.retreiveScoreBoardData(null).sorted(Comparator.comparing(Player::returnScore).reversed());
        if (playerObjects.isEmpty()){
            JOptionPane.showMessageDialog(null,"Data base is empty\nPlease play the game to add a new player");
        }
        playerObjects.addAll(playerObjectsNew);
        clearData(initials,nameList);
        clearData(scoreArray,scoreIDlist);
        clearData(positions,posList);
        Position = 1;
        for (int i = 0; i < playerObjects.size(); i++) {
            setupData(String.valueOf(Position),positions,posList,posProperty);
            Position++;
        }
        System.out.println(playerObjects.size());
        playerObjects.sorted(Comparator.comparing(Player::returnScore));
        for (Player name:playerObjects) {
            setupData(name.returnInitials(),initials,nameList,nameProperty);
            setupData(String.valueOf(name.returnScore()),scoreArray,scoreIDlist,scoreProperty);
        }


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
