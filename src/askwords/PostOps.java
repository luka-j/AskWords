package askwords;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luka
 */
public class PostOps {

    public static void op(String... args) {
        String file = args[args.length - 1], name;
        WordCounter base = new WordCounter(file + ".words");
        for (int i = 0; i < args.length - 1; i++) {
            name = args[i];
            switch (name) {
                case "filter":
                case "filter3":
                case "filter3plus":
                case "3+":
                    base = filter3Plus(base);
                    break;
                case "filter4":
                case "filter4plus":
                case "4+":
                    base = filter4Plus(base);
                    break;
                case "lowercase":
                    base = toLowercase(base);
                    break;
                case "latin":
                    base = toLatin(base);
                    break;
                case "symbols":
                    base = stripSymbols(base);
                    break;
                case "single":
                    base = stripDoubleLetters(base);
                    break;
                case "čć":
                case "clear":
                    base = stripGuillemets(base);
                    break;
                case "koreni":
                case "stripsuffix":
                case "generify":
                    base = generify(base);
                    break;
            }
        }
        try {
            base.saveScores();
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static WordCounter filter3Plus(WordCounter base) {
        WordCounter tplus = new WordCounter("3plus" + base.getName());
        try {
            base.loadScores();
            base.scores.forEach((String word, Integer count) -> {
                if(word.length()>2)
                    tplus.putWords(word, count);
            });
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tplus;
    }

    private static WordCounter filter4Plus(WordCounter base) {
        WordCounter fplus = new WordCounter("4plus" + base.getName());
        try {
            base.loadScores();
            base.scores.forEach((String word, Integer count) -> {
                if(word.length()>3)
                    fplus.putWords(word, count);
            });
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fplus;
    }

    private static WordCounter toLowercase(WordCounter base) {
        WordCounter lowercase = new WordCounter("lowercase." + base.getName());
        try {
            base.loadScores();
            base.scores.forEach((String word, Integer count) -> {
                lowercase.putWords(word.toLowerCase(), count);
            });
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lowercase;
    }

    private static WordCounter toLatin(WordCounter base) {
        WordCounter latin = new WordCounter("latin." + base.getName());
        try {
            base.loadScores();
            base.scores.forEach((String word, Integer count) -> {
                latin.putWords(LetterUtils.toLatin(word), count);
            });
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return latin;
    }

    private static WordCounter stripDoubleLetters(WordCounter base) {
        WordCounter singleLet = new WordCounter("singlelet." + base.getName());
        try {
            base.loadScores();
            base.scores.forEach((String word, Integer count) -> {
                singleLet.putWords(LetterUtils.discardDoubleLetters(word), count);
            });
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return singleLet;
    }

    private static WordCounter stripSymbols(WordCounter base) {
        WordCounter noSymbols = new WordCounter("nosymbols." + base.getName());
        try {
            base.loadScores();
            base.scores.forEach((String word, Integer count) -> {
                noSymbols.putWords(LetterUtils.removeSymbols(word), count);
            });
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noSymbols;
    }

    private static WordCounter stripGuillemets(WordCounter base) {
        WordCounter noČĆŽĐŠ = new WordCounter("clean." + base.getName());
        try {
            base.loadScores();
            base.scores.forEach((String word, Integer count) -> {
                noČĆŽĐŠ.putWords(LetterUtils.stripGuillemets(word), count);
            });
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noČĆŽĐŠ;
    }

    private static WordCounter generify(WordCounter base) {
        String name;
        if (base.getName().contains("lowercase") && base.getName().contains("latin")
                && base.getName().contains("nosymbols") && base.getName().contains("singlelet")
                && base.getName().contains("clean")) {
            name = "generic.words";
        } else {
            name = "koreni." + base.getName();
        }
        WordCounter koreni = new WordCounter(name);
        try {
            base.loadScores();
            base.scores.forEach((String word, Integer count) -> {
                koreni.putWords(LetterUtils.stripSuffix(word), count);
            });
        } catch (IOException ex) {
            Logger.getLogger(PostOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return koreni;
    }
}
