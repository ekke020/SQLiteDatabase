package lorem;

import com.thedeanda.lorem.LoremIpsum;

import java.util.Arrays;

public class LoremGenerator {

    public static String getLorem(int min, int max) {
        return LoremIpsum.getInstance().getWords(min, max);
    }

}
