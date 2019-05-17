package sample;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
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

    private static final String link = "greseam";
    private static final String linked = "1544Mar23";

    private static final String Url = "jdbc:mysql://db4free.net:3306/finalproject154?verifiyServerCertificate=false&useSSL=false";
    @FXML
    public ListView nameList;
    public ListView scoreIDlist;
    public ListView posList;


    Stage Rules = new Stage();
    Stage window = new Stage();
    Stage Scoreboard = new Stage();
    ArrayList<String> commonNames = new ArrayList<String>();
    static Thread thread = new Thread();
    String CorrectGuess;
    ArrayList<Player> data = new ArrayList<>();
    ArrayList<String> scoreArray = new ArrayList<>();
    ArrayList<String> initials = new ArrayList<>();
    ArrayList<String> positions = new ArrayList<>();
    ListProperty<String> scoreProperty = new SimpleListProperty<>();
    ListProperty<String> nameProperty = new SimpleListProperty<>();
    ListProperty<String> posProperty = new SimpleListProperty<>();
    String initials1 = "";
    int Position = 1;
    int guessLimit = -1; //set to negative to initalize the first correct value(right answer)
    int roundLimit = 4;
    int score = 0;//set to 0 as player shouldn't start with points
    int countBull=0;//initial number of correct char in the correct position
    int countCow =0;//initial number of correct char in wrong position
    String[] hintList;//list used for easier format

    //methods to be used in controller

    public void generateNewStages() throws IOException, SQLException, ClassNotFoundException {
        Rules.close();
        window.setTitle("Guess the Animal I am Thinking of!");
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("SecondFrame.fxml")), 640, 400));


        Scoreboard.setTitle("Scoreboard");
        Scoreboard.setScene(new Scene(FXMLLoader.load(getClass().getResource("Scoreboard.fxml")), 640, 400));
        Scoreboard.show();
        window.show();
    }//generates the scoreboard and game

    //this is run every time the user guesses, and regenerates new words each round (after "guessLimit")
    public String makeGuess(String guess,boolean runGuess) throws IOException, InterruptedException {
        boolean victory = false;
        boolean hasRun;//used to break the while loop
        String content = "";
        String buttonContent = "Make Guess";
        System.out.println("Initializing");
        String rightAnwser = setListVar(guessLimit);
        rightAnwser = rightAnwser.toLowerCase();
        String gameDescript;
        gameDescript = setGameDescription(rightAnwser);
        if (runGuess == true ) {
            if (roundLimit < 0){roundLimit = 4;}
            if (guessLimit <= -1) {
                if (roundLimit<0){score = 0;}
                guessLimit = 8;
                roundLimit--;
            } else if (guessLimit >= 0) {
                System.out.println(rightAnwser.length());
                hasRun = false;
                while (guessLimit >= 0 && hasRun == false) {
                    System.out.println("Checking");
                    //checks guess

                    //not empty so that its positive their is a value to check
                    if (!guess.isEmpty()) {
                        System.out.println("Word is not null");
                        if (guess.length() != rightAnwser.length()) {
                            JOptionPane.showMessageDialog(null, "the length of your guess wasn't long or short enough", "Notice", 2);
                            break;
                        } else {
                            if (rightAnwser.toLowerCase().equals(guess.toLowerCase())) {
                                System.out.println("Victory");
                                JOptionPane.showMessageDialog(null, "Congratulations you figured out the animal i was thinking of!\nPress the button to get the next one.", "Victory!!!", 1);
                                victory = true;
                                countBull = rightAnwser.length();
                                countCow = rightAnwser.length();
                                score = score + (guessLimit + 1) * 100;
                                buttonContent = "Generate Next Word";
                                System.out.println("Score: " + score);
                                guessLimit = 0;
                            } else {
                                hintList = getHint(rightAnwser, guess).split(",");
                                countCow = Integer.valueOf(hintList[1]);
                                countBull = Integer.valueOf(hintList[0]);
                                System.out.println("Were running down town");
                                System.out.println(getHint(rightAnwser, guess));
                            }
                        }

                    } else if (guess.isEmpty()) {
                        System.out.println("Null!");
                        JOptionPane.showMessageDialog(null, "Error: Please enter a guess!", "Error", 2);
                        guessLimit++;
                    } else {
                        System.out.println("??");

                    }


                    if (guessLimit == 0 && victory == false) {
                        JOptionPane.showMessageDialog(null, "you have run out of guess for this word.\n-100 Points\nThinking of a new animal!", "Notice", 2);
                        score = score - 100;
                        buttonContent = "Generate Next Word";
                        guessLimit = 0;
                    }
                    if (roundLimit <= 0 && guessLimit ==0 && victory == true){
                        Position =1;
                        for (int i = 0; i < data.size(); i++) {
                            Position++;
                        }
                          initials1 = JOptionPane.showInputDialog(null,"You have run out of Rounds\nPlease enter your initials to save your score","Initials",3);
                          Player player = new Player(initials1,score);
                          System.out.println("Player score: "+player.score);
                          AddToDatabase(null,null,player);
                          roundLimit--;
//                        data.add(player);
//                        setupData(player.returnInitials(),initials,nameList,nameProperty);
//                        setupData(String.valueOf(player.returnScore()),scoreArray,scoreIDlist,scoreProperty);

                    }
                    guessLimit--;
                    hasRun = true;

                }
            } else {
            }
            System.out.println("Score: " + score);
            System.out.println(guessLimit);
            content = buttonContent + "," + countBull + "," + countCow + "," + gameDescript + "," + guessLimit + "," + score + "," + victory + "," + guess+","+initials1+","+roundLimit;
            return content;
        }else {
            return String.valueOf(score);
        }
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

            HashMap<Character, Integer> map = new HashMap<Character, Integer>();

            //check bull
            for(int i=0; i<secret.length(); i++){
                char c1 = secret.toLowerCase().charAt(i);
                char c2 = guess.toLowerCase().charAt(i);

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
                char c1 = secret.toLowerCase().charAt(i);
                char c2 = guess.toLowerCase().charAt(i);

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

        return countBull+","+countCow;
    }
    public int getScore(){
        return score;
    }

    //SQL methods

    void enter_institution() throws SQLException {
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

    public void AddToDatabase(Connection conn,Statement stmt ,Player newPlayer) {
        try {
            //STEP 2: Register JDBC driver
            //Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(Url, link, linked);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = "INSERT INTO Players (Initials, Score) VALUES ('" + newPlayer.returnInitials() + "'," + newPlayer.returnScore() + ")"; //INSERT INTO `Players`(`Initials`, `Score`) VALUES ([value-1],[value-2])
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public void RemoveAllFromDatabase(Connection conn,Statement stmt) throws SQLException {
        try {
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(Url, link, linked);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Removing records from the table...");
            stmt = conn.createStatement();
            String $Score = "Score";
            String sql = "DELETE FROM Players WHERE Score = 0 || Score > 0";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
        //Handle errors for JDBC
        se.printStackTrace();
    } catch (Exception e) {
        //Handle errors for Class.forName
        e.printStackTrace();
    } finally {
        //finally block used to close resources
        try {
            if (stmt != null)
                conn.close();
        } catch (SQLException se) {
        }// do nothing
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }//end finally try
    }//end try
        System.out.println("Data deleted successfully! Goodbye!");
    }

    public ObservableList<Player> retreiveScoreBoardData(Connection conn) throws SQLException, ClassNotFoundException {
        String Initials,Score;

            Statement sqlSatement = null;
            ResultSet results = null;
            ObservableList<Player> ListofPlayer = FXCollections.observableArrayList();
        try {
            if (conn == null) {
                Connection myConn = DriverManager.getConnection(Url, link, linked);
                System.out.println("Connection made");
                String qry = "SELECT Initials,Score FROM Players";
                sqlSatement = myConn.createStatement();
                results = sqlSatement.executeQuery(qry);


                while (results.next()) {
                    Initials = results.getString(1);
                    Score = results.getString(2);
                    ListofPlayer.add(new Player(Initials, Integer.valueOf(Score)));
                }

            }
        }catch (Exception e){        conn.close();
        }
        return ListofPlayer;
    }
    public void setupData(String content, ArrayList<String > List,ListView View,ListProperty listProperty){
        try {
            List.add(content);
        }catch (Exception e){
        }
        listProperty.set(FXCollections.observableArrayList(List));
        try {
            View.itemsProperty().bind(listProperty);
        }catch (Exception e){System.out.println(listProperty.toString()+List);}

    }
    //
}

