package sample;

import com.sun.media.jfxmedia.events.NewFrameEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

public class Main extends Application {

    //Main Run = new Main();

    @Override
    public void start(Stage primaryStage) throws Exception{
        int Width = 640;
        int Hieght = 350;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Rules!");
        primaryStage.setScene(new Scene(root, Width, Hieght));
        primaryStage.show();

        //set up connection


    }




    public static void main(String[] args) {
        launch(args);
    }
}

