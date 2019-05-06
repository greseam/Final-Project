package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


import javax.swing.*;

public class Controller {

    public static int ROUND_LENGTH; // constant time interval for each round, 2 min.
    Main Run = new Main();

    Stage window = new Stage();
    @FXML
    public void Start(ActionEvent actionEvent) throws IOException {
        window.setTitle("Guess the Animal I am Thinking of!");
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("SecondFrame.fxml")), 640, 400));
        window.show();
        JOptionPane.showMessageDialog(null,"New window","Guess",2);

        //MainFrame.getChildren().setAll(new AnchorPane(FXMLLoader.load(getClass().getResource("secondframe.fxml"))));
    }

    public void Makeguess(ActionEvent actionEvent) {
        int keeprunning = 1;
        while (keeprunning == 1){
            if (true){
                JOptionPane.showMessageDialog(null,"Error: Please enter a guess!","Error",2);
            }
            else {
                keeprunning = 0;
            }
        }
        JOptionPane.showMessageDialog(null,"success","",1);


    }
}
