package sample;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.scene.control.*;
import javax.swing.*;
import java.io.IOException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class methods {

    Stage Rules = new Stage();
    Stage window = new Stage();
    Stage Scoreboard = new Stage();
    ArrayList<String> commonNames = new ArrayList<String>();
    static Thread thread = new Thread();
    String CorrectGuess;
    int guessLimit = -1; //set to negative to initalize the first correct value(right answer)
    int score = 0;//set to 0 as player shouldn't start with points
    int countBull=0;//initial number of correct char in the correct position
    int countCow =0;//initial number of correct char in wrong position
    String[] hintList;//list used for easier format

    //methods to be used in controller

    public void generateNewStages() throws IOException {
        Rules.close();
        window.setTitle("Guess the Animal I am Thinking of!");
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("SecondFrame.fxml")), 640, 400));


        Scoreboard.setTitle("Scoreboard");
        Scoreboard.setScene(new Scene(FXMLLoader.load(getClass().getResource("Scoreboard.fxml")), 640, 400));
        Scoreboard.show();
        window.show();
    }//generates the scoreboard and game

    //this is run every time the user guesses, and regenerates new words each round (after "guessLimit")
    public String makeGuess(String guess) throws IOException, InterruptedException {
        boolean victory = false;
        boolean hasRun;//used to break the while loop
        String content = "";
        String buttonContent = "Make Guess";
        System.out.println("Initializing");
        String rightAnwser = setListVar(guessLimit);
        rightAnwser = rightAnwser.toLowerCase();
        String gameDescript;
        gameDescript = setGameDescription(rightAnwser);

        if (guessLimit == -1){
            guessLimit = 8;
        }else if (guessLimit > 0){
            System.out.println(rightAnwser.length());
            hasRun = false;
            while (guessLimit > 0 && hasRun == false) {
                System.out.println("Checking");
                //checks guess

                //not empty so that its positive their is a value to check
                if (!guess.isEmpty()) {
                    System.out.println("Word is not null");

                    if (rightAnwser.equals(guess)) {
                        System.out.println("Victory");
                        JOptionPane.showMessageDialog(null,"Congratulations you figured out the animal i was thinking of!\nPress the Make Guess button to figure the next animal im thinking of.","Victory!!!",1);
                        victory = true;
                        countBull= rightAnwser.length();
                        countCow= rightAnwser.length();
                        score = score + (guessLimit+ 1) * 100;
                        buttonContent = "Generate Next Word";
                        System.out.println("Score: "+score);
                        guessLimit = 0;
                    } else {
                        hintList = getHint(rightAnwser,guess).split(",");
                        countCow = Integer.valueOf(hintList[1]);
                        countBull = Integer.valueOf(hintList[0]);
                        //string comparison methods
                        System.out.println("Were running down town");
                            System.out.println(getHint(rightAnwser,guess));



                    }
                } else if (guess.isEmpty()) {
                    System.out.println("Null!");
                    JOptionPane.showMessageDialog(null, "Error: Please enter a guess!", "Error", 2);
                    guessLimit++;
                } else {
                    System.out.println("??");

                }
                hasRun = true;
                guessLimit--;
                if (guessLimit == 0 && victory == false) {
                    JOptionPane.showMessageDialog(null, "you have run out of guess for this word.\n-100 Points\nThinking of a new animal!", "Notice", 2);
                    guessLimit = -1;
                }

            }
        }else {
        }
        System.out.println("Score: " +score);
        System.out.println(guessLimit);
        System.out.println(content);
        content = buttonContent +","+ countBull +","+ countCow +","+gameDescript+","+guessLimit+","+score; //+","+Roundtimer(5)
        return content;
    }
    public int Roundtimer(int minutes) throws InterruptedException {
        int timerValue =0;
        for(int i =60*minutes; i>0;i--)
        {
            thread.sleep(1000);
            System.out.println(i);
            timerValue = i;
        }
        return timerValue;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }//function used to randomly acquire a number and used in selection of animals

    public String setListVar(int guessLimit) throws IOException {
        Document doc = Jsoup.connect("https://lib2.colostate.edu/wildlife/atoz.php?sortby=genus_species&letter=all").get();

        //System.out.println(doc.title());
        Elements group = doc.select("table.names");
        Elements animal =  group.select("td");
        for(int i = 0; i < animal.size(); i +=2)
        {
            try {
                commonNames.add(cleaner(animal.get(i).text()));
            }
            catch (Exception e){
                commonNames.add(animal.get(i).text());
            }

        }
        for (int i = 0; i < commonNames.size(); i++) {
            System.out.println(commonNames.get(i));
        }
        if (guessLimit < 0) {
            CorrectGuess = commonNames.get(randInt(0, commonNames.size()));
        }
        System.out.println("#"+CorrectGuess);
        return CorrectGuess;
    }

    public String cleaner(String Text){
        try {
            Text = Text.replace("(unidentified)","");
        }
        catch (Exception e){

        }

        try {
            Text =Text.split(",")[1].replace(" ","") + " "+ Text.split(",")[0];
        }
        catch (Exception e){

        }
        try{
            String[] TextList = Text.split(" ");
            Text = Text.split(" ")[TextList.length - 1];
        }
        catch (Exception e){

        }

        return Text;
    }//cleaner is used to format the incoming strings and change them into the common names of the animals
    public String setGameDescription(String correctGuess){
        int nameLength = 0;
        nameLength = correctGuess.length();
        String content ="Can you guess the "+Integer.valueOf(nameLength)+" character Animal name I am thinking of?";
        return content;
    }
    public String setButtonDescription(){
        String content = "Make Guess";
        return content;
    }
    public String getHint(String secret, String guess) {
        countBull=0;
        countCow =0;
        int[] arr1 = new int[10];
        int[] arr2 = new int[10];
        if (secret.length() == guess.length()) {
            HashMap<Character, Integer> map = new HashMap<Character, Integer>();

            //check bull
            for(int i=0; i<secret.length(); i++){
                char c1 = secret.charAt(i);
                char c2 = guess.charAt(i);

                if(c1==c2){
                    countBull++;
                }else{
                    if(map.containsKey(c1)){
                        int freq = map.get(c1);
                        freq++;
                        map.put(c1, freq);
                    }else{
                        map.put(c1, 1);
                    }
                }
            }

            //check cow
            for(int i=0; i<secret.length(); i++){
                char c1 = secret.charAt(i);
                char c2 = guess.charAt(i);

                if(c1!=c2){
                    if(map.containsKey(c2)){
                        countCow++;
                        if(map.get(c2)==1){
                            map.remove(c2);
                        }else{
                            int freq = map.get(c2);
                            freq--;
                            map.put(c2, freq);
                        }
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "the length of your guess wasn't long or short enough", "Notice", 2);

        }
        return countBull+","+countCow;
    }

    //scoreboard and SQL methods
    public void updateScoreboard(){
        //put both SQL methods in here
    }
    void enter_institution(ActionEvent event) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("https://www.db4free.net/phpMyAdmin/index.php?target=server_sql.php","greseam","1544Mar23");
        System.out.println("Connected");

        String sql = "INSERT INTO Institution (instName, instAddress, instWebsite, instPhone, instCampSetting, instPopulation, instStuFacRatio, instEnrollment)"+
                "VALUES (?, ?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
       //preparedStatement.setString(1,institution_name_txtbx.getText());

       preparedStatement.executeUpdate();
       System.out.println("Record Inserted");



    }
    void updateInstitution() throws SQLException{
        //ObservableList<in>
    }
}

