package sample;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Animals {

    public String name;
    public int Popularity;
    public Animals(String name, int Popularity) {
        this.name = name;
        this.Popularity= Popularity;
        //sort into list
    }

    public String RanName(ArrayList<Animals> Animals){
        String Guess;
        Guess = Animals.get(randInt(0,100)).name;
        return Guess;
    }
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
