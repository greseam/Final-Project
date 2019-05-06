package sample;

import com.sun.media.jfxmedia.events.NewFrameEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

import javax.swing.*;

public class Main extends Application {

    //Main Run = new Main();
    Stage Rules = new Stage();
    Stage window = new Stage();
    Stage Scoreboard = new Stage();
    @FXML
    TextField GuessBox;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Rules = primaryStage;
        int Width = 640;
        int Hieght = 350;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Rules.setTitle("Rules!");
        Rules.setScene(new Scene(root, Width, Hieght));
        Rules.show();

    }
    public static void main(String[] args) {
        launch(args);

    }

    //methods to be used in controller

    public String GenerateRandomGuess(){
        String Name;
        Name = " ";
        return Name;
    }

    public void generateNewStages() throws IOException {

        window.setTitle("Guess the Animal I am Thinking of!");
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("SecondFrame.fxml")), 640, 400));
        window.show();

        Scoreboard.setTitle("Scoreboard");
        Scoreboard.setScene(new Scene(FXMLLoader.load(getClass().getResource("Scoreboard.fxml")), 640, 400));
        Scoreboard.show();
    }

    public void makeGuess(){
        int runOnce = 1;
        while (runOnce == 1) {
            int keeprunning = 1;
            while (keeprunning == 1) {
                if (GuessBox == null) {
                    JOptionPane.showMessageDialog(null, "Error: Please enter a guess!", "Error", 2);
                    keeprunning = 0;
                } else {
                    keeprunning = 0;
                    JOptionPane.showMessageDialog(null, "success", "", 1);
                }
            }
            runOnce--;
        }
    }
}

