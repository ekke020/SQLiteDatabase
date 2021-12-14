package model;

public class TextFormatter {

    public static String formatText(String text){
        String[] words = text.trim().split(" ");
        StringBuilder sb = new StringBuilder("\t");
        int length = 1;
        for (String word : words) {
            length += word.length() + 1;
            if (length >= 60) {
                sb.append("\n\t");
                length = 0;
                length += word.length() +1;
            }
            sb.append(word).append(" ");
        }
        return sb.toString();
    }

}
