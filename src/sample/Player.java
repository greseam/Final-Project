package sample;


public class Player {
    public String initials;
    public int score;
    public Player(String initials,int score) {
        this.initials = initials;
        this.score = score;
    }
    public int returnScore(){
        return score;
    }
    public String returnInitials(){
        return initials;
    }
}
