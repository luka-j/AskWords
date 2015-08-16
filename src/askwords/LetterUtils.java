package askwords;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author luka
 */
public class LetterUtils {

    private static final Map<Character, String> toLatin = new HashMap<>();
    private static final Map<Character, String> stripGuillemets = new HashMap<>();
    private static final Set<Character> symbols = new HashSet<>();
    private static final Map<Integer, Set<String>> suffixes = new HashMap<>();
    private static final Set<String> exceptions = new HashSet<>();
    private static final Set<String> exceptions1 = new HashSet<>();

    static {
        toLatin.put('љ', "lj");
        toLatin.put('њ', "nj");
        toLatin.put('е', "e");
        toLatin.put('р', "r");
        toLatin.put('т', "t");
        toLatin.put('з', "z");
        toLatin.put('у', "u");
        toLatin.put('и', "i");
        toLatin.put('о', "o");
        toLatin.put('п', "p");
        toLatin.put('ш', "š");
        toLatin.put('ђ', "đ");
        toLatin.put('а', "a");
        toLatin.put('с', "s");
        toLatin.put('д', "d");
        toLatin.put('ф', "f");
        toLatin.put('г', "g");
        toLatin.put('х', "h");
        toLatin.put('ј', "j");
        toLatin.put('к', "k");
        toLatin.put('л', "l");
        toLatin.put('ч', "č");
        toLatin.put('ћ', "ć");
        toLatin.put('ж', "ž");
        toLatin.put('џ', "dž");
        toLatin.put('ц', "c");
        toLatin.put('в', "v");
        toLatin.put('б', "b");
        toLatin.put('н', "n");
        toLatin.put('м', "m");

        stripGuillemets.put('š', "s");
        stripGuillemets.put('đ', "dj");
        stripGuillemets.put('č', "c");
        stripGuillemets.put('ć', "c");
        stripGuillemets.put('ž', "z");

        symbols.add(',');
        symbols.add('.');
        symbols.add('!');
        symbols.add('?');
        symbols.add('-');
        symbols.add('_');
        symbols.add('"');
        symbols.add('\'');
        symbols.add(':');
        symbols.add(';');
        symbols.add(')');
        symbols.add('(');
        symbols.add('<');
        symbols.add('3');
        symbols.add('>');
        symbols.add('♥');
        symbols.add('♡');
        symbols.add('❤');
        symbols.add('^');
        symbols.add('#');
        symbols.add('&');
        symbols.add('{');
        symbols.add('}');
        symbols.add('\\');
        symbols.add('=');
        symbols.add('%');
        symbols.add('*');
        symbols.add('░');
        symbols.add('█');
        symbols.add('▄');
        symbols.add('▀');
        
        Set suffix1 = new HashSet<>();
        Set suffix2 = new HashSet<>();
        Set suffix3 = new HashSet<>();
        Set suffix4 = new HashSet<>();
        suffix1.add("a");
        suffix1.add("e");
        suffix1.add("i");
        suffix1.add("o");
        suffix1.add("u");
        suffix1.add("m");
        suffix1.add("s");
        suffix2.add("la");
        suffix2.add("mo");
        suffix2.add("om");
        suffix2.add("te");
        suffix2.add("li");
        suffix2.add("ti");
        suffix2.add("cu");
        suffix2.add("ce");
        suffix2.add("oj");
        suffix2.add("aj");
        suffix2.add("uj");
        suffix2.add("og");
        suffix2.add("si");
        suffix3.add("jte");
        suffix3.add("aju");
        suffix3.add("uju");
        suffix3.add("jmo");
        suffix3.add("ces");
        suffix3.add("ati");
        suffix4.add("cete");
        suffix4.add("cemo");
        suffix3.add("jemo");
        
        suffixes.put(1, suffix1);
        suffixes.put(2, suffix2);
        suffixes.put(3, suffix3);
        suffixes.put(4, suffix4);
        
        exceptions.add("osecaj");
        exceptions.add("osjecaj");
        exceptions.add("zafgrljaj");
        exceptions.add("nemoj");
        exceptions.add("kojoj");
        exceptions.add("nekoj");
        exceptions1.add("drugoj");
        exceptions.add("hvala");
        exceptions1.add("skola");
        exceptions.add("nikola");
        exceptions.add("andjela");
        exceptions1.add("profila");
        exceptions.add("godiste");
        exceptions1.add("brate");
        exceptions.add("uopste");
        exceptions1.add("skoli");
        exceptions.add("misli");
        exceptions.add("smajli");
        exceptions.add("srce");
        exceptions1.add("srece");
        exceptions.add("sunce");
        exceptions.add("nalog");
        exceptions.add("onda");
        exceptions.add("sutra");
        exceptions.add("nema");
        exceptions.add("svidja");
        exceptions.add("mozda");
        exceptions.add("moze");
        exceptions.add("like");
        exceptions.add("nije");
        exceptions.add("odakle");
        exceptions.add("inace");
        exceptions.add("samo");
        exceptions.add("nesto");
        exceptions.add("tako");
        exceptions.add("zasto");
        exceptions.add("nego");
        exceptions.add("jako");
        exceptions.add("zato");
        exceptions.add("cemu");
        exceptions.add("njemu");
        exceptions.add("film");
        exceptions.add("tobom");
        exceptions.add("problem");
        exceptions.add("nekim");
        exceptions.add("njim");
        exceptions.add("tvom");
        exceptions.add("njom");
        exceptions.add("kojom");
        exceptions.add("datum");
        exceptions.add("svom");
        exceptions.add("barem");
        exceptions.add("svojim");
        exceptions.add("ovim");
        exceptions.add("danas");
        exceptions.add("status");
        exceptions.add("veceras");
        exceptions.add("fejs");
        exceptions.add("opis");
    }
    
    public static String toLatin(String word) {
        StringBuilder latin = new StringBuilder();
        for(int i=0; i<word.length(); i++) {
            latin.append(toLatin.getOrDefault(word.charAt(i), String.valueOf(word.charAt(i))));
        }
        return latin.toString();
    }
    
    public static String stripSuffix(String word) {
        if(word.length() > 6
                && suffixes.get(4).contains(word.substring(word.length()-4))
                && !exceptions.contains(word) && !exceptions1.contains(word))
            word = word.substring(0, word.length()-4);
        else if(word.length() > 5
                && suffixes.get(3).contains(word.substring(word.length()-3))
                && !exceptions.contains(word) && !exceptions1.contains(word))
            word = word.substring(0, word.length()-3);
        else if(word.length() > 4
                && suffixes.get(2).contains(word.substring(word.length()-2))
                && !exceptions.contains(word) && !exceptions1.contains(word))
            word = word.substring(0, word.length()-2);
        else if(word.length() > 3
                && suffixes.get(1).contains(word.substring(word.length()-1))
                && !exceptions.contains(word))
            word = word.substring(0, word.length()-1);
        return word;
    }
    
    public static String stripGuillemets(String word) {
        StringBuilder noGuillemets = new StringBuilder();
        for(int i=0; i<word.length(); i++) {
            noGuillemets.append(stripGuillemets.getOrDefault(word.charAt(i), String.valueOf(word.charAt(i))));
        }
        return noGuillemets.toString();
    }
    
    public static String discardDoubleLetters(String word){
        StringBuilder singleLetter = new StringBuilder().append(word.charAt(0));
        for(int i=1; i<word.length(); i++) {
            if(word.charAt(i) != word.charAt(i-1))
                singleLetter.append(word.charAt(i));
        }
        return singleLetter.toString();
    }
    
    public static String removeSymbols(String word) {
        StringBuilder noSymbols = new StringBuilder();
        for(int i=0; i<word.length(); i++) {
            if(!symbols.contains(word.charAt(i)))
                noSymbols.append(word.charAt(i));
        }
        return noSymbols.toString();
    }
}
