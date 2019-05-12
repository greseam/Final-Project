package sample;

import java.lang.Object;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

import javax.swing.*;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        int Width = 640;
        int Hieght = 350;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Rules!");
        primaryStage.setScene(new Scene(root, Width, Hieght));
        primaryStage.show();

    }


    public static void main(String[] args) throws InterruptedException {
        launch(args);


    }



}

