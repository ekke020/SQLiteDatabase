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
                "\n" + Colors.RED + formatText(text) + Colors.RESET + "\n";
    }

    private String formatText(String text){
        String[] words = text.trim().split(" ");
        StringBuilder sb = new StringBuilder("\t");
        int length = 0;
        for (String word : words) {
            length += word.length() + 1;
            if (length > 60) {
                sb.append("\n\t");
                length = 0;
                length += word.length() +1;
            }
            sb.append(word).append(" ");
        }
        return sb.toString();
    }
}
