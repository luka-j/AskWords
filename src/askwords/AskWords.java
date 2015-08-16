package askwords;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author luka
 */
public class AskWords {

    static File workFolder = new File("/home/luka/Documents/askfm/");


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if(args.length > 0) {
            switch(args[0]) {
                case "op":
                    String[] ops = Arrays.copyOfRange(args, 1, args.length);
                    PostOps.op(ops);
                    break;
                case "process":
                    Process.populateWords();
                    break;
            }
        } else {
            Process.populateWords();
        }
    }

}
