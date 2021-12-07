package lorem;

import com.thedeanda.lorem.LoremIpsum;

import java.util.Arrays;

public class LoremGenerator {

    public static String[] getLorem(int min, int max) {
        String[] sentences = LoremIpsum.getInstance().getParagraphs(min, max).split("\\.");
        Arrays.stream(sentences).forEach(sentence -> sentence = sentence.trim() + ".");

        return sentences;
    }

}
