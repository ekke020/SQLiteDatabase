package model;

public class Comment {

    private final String text;
    private final String posterName;
    private final String timeStamp;
    private final int index;

    public String getText() {
        return text;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getIndex() {
        return index;
    }

    public Comment(String text, String posterName, String timeStamp, int index) {
        this.text = text;
        this.posterName = posterName;
        this.timeStamp = timeStamp;
        this.index = index;
    }

    @Override
    public String toString() {
        return Colors.PURPLE + "\t" + posterName + Colors.RESET +
                " -- " + Colors.PURPLE + timeStamp + Colors.RESET +
                "\n\t" + Colors.RED + text + Colors.RESET + "\n";
    }
}
