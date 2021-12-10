package model;

public class Comment {

    private final String text;
    private final String posterName;
    private final String timeStamp;
    private final int index;


    public Comment(String text, String posterName, String timeStamp, int index) {
        this.text = text;
        this.posterName = posterName;
        this.timeStamp = timeStamp;
        this.index = index;
    }

}
