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

import java.awt.*;
import java.io.IOException;


import javax.swing.*;
import javax.swing.text.html.ListView;

public class Controller {

    Main Run = new Main();

    @FXML
    public void Start(ActionEvent actionEvent) throws IOException {
        Run.generateNewStages();
    }

    public void Makeguess(ActionEvent actionEvent) {
       Run.makeGuess();

    }
}
